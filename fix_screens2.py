import os
import re

base_dir = "app/src/main/java/com/example/ui/screens"
files = ["HomeScreen.kt", "LearnScreen.kt", "GamesScreen.kt", "VideosScreen.kt", "ZeAIScreen.kt", "MaisScreen.kt", "CreativeWorkshopScreen.kt"]

for filename in files:
    filepath = os.path.join(base_dir, filename)
    with open(filepath, "r") as f:
        content = f.read()
    
    # Fix double signature issue
    if filename == "HomeScreen.kt":
        content = re.sub(r'fun HomeScreen\(\s*viewModel: MainViewM\s*viewModel: MainViewModel,\s*onNavigate: \(Screen\) -> Unit,\s*modifier: Modifier = Modifier\s*\) \{',
                         r'fun HomeScreen(\n    viewModel: MainViewModel,\n    onNavigate: (Screen) -> Unit,\n    modifier: Modifier = Modifier\n) {', content)
    elif filename == "CreativeWorkshopScreen.kt":
        content = re.sub(r'fun CreativeWorkshopScreen\(\s*mainViewModel: MainViewM\s*mainViewModel: MainViewModel,\s*onBack: \(\) -> Unit\s*\) \{',
                         r'fun CreativeWorkshopScreen(\n    mainViewModel: MainViewModel,\n    onBack: () -> Unit\n) {', content)
    else:
        # others: mainViewModel: MainViewM or viewModel: MainViewM
        content = re.sub(r'fun ([a-zA-Z0-9_]+)\(\s*(mainV|v)iewModel: MainViewM\s*(mainV|v)iewModel: MainViewModel\s*\) \{',
                         r'fun \1(\n    \2iewModel: MainViewModel\n) {', content)
    
    with open(filepath, "w") as f:
        f.write(content)
    print(f"Cleaned {filename}")

