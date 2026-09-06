import re
import os

files_to_wrap = {
    "HomeScreen.kt": "HomeScreen",
    "LearnScreen.kt": "LearnScreen",
    "GamesScreen.kt": "GamesScreen",
    "VideosScreen.kt": "VideosScreen",
    "ZeAIScreen.kt": "ZeAIScreen",
    "MaisScreen.kt": "MaisScreen",
    "CreativeWorkshopScreen.kt": "CreativeWorkshopScreen"
}

base_dir = "app/src/main/java/com/example/ui/screens"

for filename, func_name in files_to_wrap.items():
    filepath = os.path.join(base_dir, filename)
    with open(filepath, "r") as f:
        content = f.read()
    
    match = re.search(r'(fun\s+' + func_name + r'\s*\([^)]*\)\s*\{)', content)
    if not match:
        print(f"Could not find function {func_name} in {filename}")
        continue
    
    func_start = match.end()
    
    open_braces = 1
    i = func_start
    while i < len(content) and open_braces > 0:
        if content[i] == '{':
            open_braces += 1
        elif content[i] == '}':
            open_braces -= 1
        i += 1
    
    func_end = i - 1
    
    inner_content = content[func_start:func_end]
    
    if "FundoApp" in inner_content:
        print(f"{filename} is already wrapped.")
        continue
    
    if "import com.example.ui.components.FundoApp" not in content:
        import_stmt = "import com.example.ui.components.FundoApp\n"
        content = content.replace("import androidx.compose.runtime.Composable", 
                                  "import androidx.compose.runtime.Composable\n" + import_stmt)
    
    new_inner_content = "\n    FundoApp {\n" + inner_content + "\n    }\n"
    new_content = content[:func_start] + new_inner_content + content[func_end:]
    
    with open(filepath, "w") as f:
        f.write(new_content)
    print(f"Wrapped {filename}")
