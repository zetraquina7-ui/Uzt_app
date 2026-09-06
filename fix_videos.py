import re

with open('VideosScreen.kt.bak', 'r') as f:
    content = f.read()

# Remove subcategories definitions
content = re.sub(r'val zeTraquinaSubCategories = remember \{[\s\S]*?^        \}', '', content, flags=re.MULTILINE)
content = re.sub(r'val cantinhoPtSubCategories = remember \{[\s\S]*?^        \}', '', content, flags=re.MULTILINE)

# Remove selectedZeSub, selectedPtSub, activeSubCategory
content = re.sub(r'var selectedZeSub.*?$', '', content, flags=re.MULTILINE)
content = re.sub(r'var selectedPtSub.*?$', '', content, flags=re.MULTILINE)
content = re.sub(r'val activeSubCategory.*?$', '', content, flags=re.MULTILINE)

# Add new activeKey
content = content.replace('val activeKey = activeSubCategory.key', 'val activeKey = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "ze_traquina_all" else "cantinho_pt_all"')

# Replace categoryTracksMap initialization
old_category_map = r"""var categoryTracksMap by remember \{
            mutableStateOf<Map<String, List<YouTubeVideoTrack>>>\(
                \(zeTraquinaSubCategories\.map \{ it\.key \} \+ cantinhoPtSubCategories\.map \{ it\.key \}\)
                    \.associateWith \{ key ->
                        if \(key == "ze_meus_videos"\) \{
                            CustomVideoStorageHelper\.loadCustomVideos\(context\)
                        \} else \{
                            VideoCacheManager\.getInstantCachedTracks\(context, key\)
                        \}
                    \}
            \)
        \}"""

new_category_map = r"""var categoryTracksMap by remember {
            mutableStateOf<Map<String, List<YouTubeVideoTrack>>>(emptyMap())
        }
        var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }"""
content = re.sub(old_category_map, new_category_map, content)

# Fix LaunchedEffect(selectedSection, activeSubCategory, refreshTrigger)
content = re.sub(r'LaunchedEffect\(selectedSection, activeSubCategory, refreshTrigger\) \{', 'LaunchedEffect(activeKey, refreshTrigger) {', content)

content = re.sub(r'if \(activeSubCategory\.isCustom \|\| activeKey == "ze_meus_videos"\) \{[\s\S]*?return@LaunchedEffect\s*\}', '', content)

# Fix loading logic
content = re.sub(r'val playlists = if \(activeKey\.startsWith\("pt_"\)\).*?\} else \{.*?\}', 'val playlists = if (activeKey == "ze_traquina_all") listOf("PLHz1Xt0IaQWM", "PLT7ZV5QsDKA4", "PLHXyMYX6Yxxc", "ze_shorts_auto") else listOf("PLWQVAYRzFnXMmKTjW1L_ofLE22ctWytot", "PL6Nz8kJ8yTb9mgVi1XdLNBEaSjkQoQkMW", "PLUNTULW6QqtVXhrW6HRAHzkFGELfIZ-cy", "PL6Nz8kJ8yTb_2O_sVP5cLGq-5LmvblTIb", "PLXpqaMB-MlNsha4jilmX1Lrh6FPkGTLJu", "PLAXFI56mD-HQ", "PLE0G9h6NddcDzleKVxnMjrWPBcCNWiUAt", "PL7fbHLFM41KeS4tWdu3h_2FjUNC-CfUXk")', content, flags=re.DOTALL)

# Add random play when switching tabs
random_play_effect = """
    val activeTracks = categoryTracksMap[activeKey] ?: emptyList()
    
    // Auto-play random video when section changes
    LaunchedEffect(activeKey, activeTracks) {
        if (activeTracks.isNotEmpty() && selectedVideoId == null) {
            selectedVideoId = activeTracks.random().videoId
        }
    }
    
    val activeVideoId = selectedVideoId ?: activeTracks.firstOrNull()?.videoId
"""
content = re.sub(r'val activeTracks = categoryTracksMap\[activeKey\] \?: emptyList\(\)\s*val activeVideoId = selectedVideoId \?: activeTracks\.firstOrNull\(\)\?\.videoId', random_play_effect, content)

# Fix immersive player call
old_immersive_call = r"""LandscapeImmersiveVideoPlayer\(
                selectedSection = selectedSection,
                onSectionSelected = \{ newSection ->
                    if \(selectedSection != newSection\) \{
                        selectedSection = newSection
                        selectedVideoId = null
                    \}
                \},
                currentSubCategories = if \(selectedSection == VideoMainSection\.ZE_TRAQUINA\) zeTraquinaSubCategories else cantinhoPtSubCategories,
                activeSubCategory = activeSubCategory,
                onSubCategorySelected = \{ newSub ->
                    if \(selectedSection == VideoMainSection\.ZE_TRAQUINA\) \{
                        selectedZeSub = newSub
                    \} else \{
                        selectedPtSub = newSub
                    \}
                    selectedVideoId = null
                \},
                activeTracks = activeTracks,
                activeVideoId = activeVideoId,
                onVideoSelected = \{ track ->
                    selectedVideoId = track\.videoId \?: track\.id
                \},
                accentColor = activeSubCategory\.accentColor
            \)"""

new_immersive_call = """LandscapeImmersiveVideoPlayer(
                selectedSection = selectedSection,
                onSectionSelected = { newSection ->
                    if (selectedSection != newSection) {
                        selectedSection = newSection
                        selectedVideoId = null
                    }
                },
                activeTracks = activeTracks,
                activeVideoId = activeVideoId,
                onVideoSelected = { track ->
                    selectedVideoId = track.videoId ?: track.id
                },
                accentColor = selectedSection.gradient.first()
            )"""
content = re.sub(old_immersive_call, new_immersive_call, content)

# Remove subcategories Row from portrait mode
content = re.sub(r'LazyRow\([\s\S]*?\}\s*\)\s*\}\s*Spacer\(modifier = Modifier\.height\(4\.dp\)\)', '', content, flags=re.MULTILINE)

# Remove from GrelhaVideosComponent call
content = re.sub(r'accentColor = activeSubCategory\.accentColor', 'accentColor = selectedSection.gradient.first()', content)


# Fix LandscapeImmersiveVideoPlayer definition
old_immersive_def = r"""private fun LandscapeImmersiveVideoPlayer\(
    selectedSection: VideoMainSection,
    onSectionSelected: \(VideoMainSection\) -> Unit,
    currentSubCategories: List<YouTubeSubCategory>,
    activeSubCategory: YouTubeSubCategory,
    onSubCategorySelected: \(YouTubeSubCategory\) -> Unit,
    activeTracks: List<YouTubeVideoTrack>,
    activeVideoId: String\?,
    onVideoSelected: \(YouTubeVideoTrack\) -> Unit,
    accentColor: Color
\) \{"""

new_immersive_def = """private fun LandscapeImmersiveVideoPlayer(
    selectedSection: VideoMainSection,
    onSectionSelected: (VideoMainSection) -> Unit,
    activeTracks: List<YouTubeVideoTrack>,
    activeVideoId: String?,
    onVideoSelected: (YouTubeVideoTrack) -> Unit,
    accentColor: Color
) {"""
content = re.sub(old_immersive_def, new_immersive_def, content)

# In LandscapeImmersiveVideoPlayer: fix timeout and remove subcategory row
content = content.replace("var mostrarMenu by remember { mutableStateOf(false) }", """var mostrarMenu by remember { mutableStateOf(false) }
    var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(mostrarMenu, lastInteractionTime) {
        if (mostrarMenu) {
            kotlinx.coroutines.delay(5000)
            mostrarMenu = false
        }
    }""")

# Add touch tracking to Row for menu
content = re.sub(r'Column\(\s*modifier = Modifier\s*\.fillMaxWidth\(\)\s*\.padding\(horizontal = 10\.dp, vertical = 4\.dp\),', 'Column(\n                modifier = Modifier\n                    .fillMaxWidth()\n                    .padding(horizontal = 10.dp, vertical = 4.dp)\n                    .pointerInput(Unit) {\n                        awaitPointerEventScope {\n                            while (true) {\n                                awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)\n                                lastInteractionTime = System.currentTimeMillis()\n                            }\n                        }\n                    },', content)

# Remove subcategory row in LandscapeImmersiveVideoPlayer
content = re.sub(r'Row\(\s*modifier = Modifier\s*\.fillMaxWidth\(\)\s*\.padding\(bottom = 4\.dp\),[\s\S]*?\}\s*\)\s*\}', '', content)

# Fix video click
content = content.replace(".clickable { mostrarMenu = !mostrarMenu }", """
                            .pointerInput(Unit) {
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)
                                        if (event.type == androidx.compose.ui.input.pointer.PointerEventType.Release) {
                                            if (mostrarMenu) {
                                                mostrarMenu = false
                                            } else {
                                                mostrarMenu = true
                                                lastInteractionTime = System.currentTimeMillis()
                                            }
                                        }
                                    }
                                }
                            }""")

with open('app/src/main/java/com/example/ui/screens/VideosScreen.kt', 'w') as f:
    f.write(content)

