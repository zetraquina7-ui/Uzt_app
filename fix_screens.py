import os

files_to_fix = {
    "HomeScreen.kt": ("    viewModel: MainViewM    FundoApp {", "    viewModel: MainViewModel,\n    onNavigate: (Screen) -> Unit,\n    modifier: Modifier = Modifier\n) {", 1),
    "LearnScreen.kt": ("    viewModel: MainViewM    FundoApp {", "    viewModel: MainViewModel\n) {", 1),
    "GamesScreen.kt": ("    mainViewModel: MainViewM    FundoApp {", "    mainViewModel: MainViewModel\n) {", 1),
    "VideosScreen.kt": ("    mainViewModel: MainViewM    FundoApp {", "    mainViewModel: MainViewModel\n) {", 1),
    "ZeAIScreen.kt": ("    mainViewModel: MainViewM    FundoApp {", "    mainViewModel: MainViewModel\n) {", 1),
    "MaisScreen.kt": ("    mainViewModel: MainViewM    FundoApp {", "    mainViewModel: MainViewModel\n) {", 1),
    "CreativeWorkshopScreen.kt": ("    mainViewModel: MainViewM    FundoApp {", "    mainViewModel: MainViewModel,\n    onBack: () -> Unit\n) {", 1)
}

base_dir = "app/src/main/java/com/example/ui/screens"

for filename, (bad_str, good_str, braces_to_remove) in files_to_fix.items():
    filepath = os.path.join(base_dir, filename)
    with open(filepath, "r") as f:
        content = f.read()
    
    # 1. Fix the function signature
    # Since I don't know the exact bad string, let's just find `FundoApp {` inside `fun FuncName`
    func_name = filename.replace(".kt", "")
    idx = content.find(f"fun {func_name}(")
    if idx != -1:
        start_fundo = content.find("FundoApp {", idx)
        if start_fundo != -1 and start_fundo - idx < 200:
            content = content[:start_fundo] + good_str + "\n" + content[start_fundo + len("FundoApp {"):]
            
            # also remove the trailing `}` that FundoApp added
            last_brace_idx = content.rfind("}")
            if last_brace_idx != -1:
                content = content[:last_brace_idx] + content[last_brace_idx+1:]
                
            with open(filepath, "w") as f:
                f.write(content)
            print(f"Fixed {filename}")
        else:
            print(f"Could not find FundoApp {{ in {filename}")

