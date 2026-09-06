import re
import os

files_to_wrap = {
    "HomeScreen.kt": "HomeScreen",
    "GamesScreen.kt": "GamesScreen",
    "ZeAIScreen.kt": "ZeAIScreen",
    "CreativeWorkshopScreen.kt": "CreativeWorkshopScreen"
}

base_dir = "app/src/main/java/com/example/ui/screens"

for filename, func_name in files_to_wrap.items():
    filepath = os.path.join(base_dir, filename)
    with open(filepath, "r") as f:
        content = f.read()
    
    # search for `fun FuncName(`
    start_idx = content.find(f"fun {func_name}(")
    if start_idx == -1:
        print(f"Could not find {func_name}")
        continue
    
    # find the '{' that opens the function body
    open_parens = 0
    i = start_idx + len(f"fun {func_name}")
    found_brace = False
    
    while i < len(content):
        if content[i] == '(':
            open_parens += 1
        elif content[i] == ')':
            open_parens -= 1
            if open_parens == 0:
                # now find the next '{'
                j = i + 1
                while j < len(content):
                    if content[j] == '{':
                        func_start = j + 1
                        found_brace = True
                        break
                    j += 1
                if found_brace:
                    break
        i += 1
    
    if not found_brace:
        print(f"Could not find body for {func_name}")
        continue
    
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
