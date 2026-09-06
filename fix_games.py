content = open("app/src/main/java/com/example/ui/screens/GamesScreen.kt").read()
import re
new_content = re.sub(r'fun GamesScreen\([\s\S]*?\) \{', 'fun GamesScreen(\n    key: String = "",\n    mainViewModel: MainViewModel? = null,\n    gamesViewModel: GamesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()\n) {', content, count=1)
open("app/src/main/java/com/example/ui/screens/GamesScreen.kt", "w").write(new_content)
