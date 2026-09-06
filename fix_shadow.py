import re

with open("app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt", "r") as f:
    content = f.read()

content = re.sub(r'\.background\(Color\.Transparent\)\s*//\s*\n\s*elevation\s*=[^\n]+\n\s*shape\s*=[^\n]+\n\s*\)', '', content)

with open("app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt", "w") as f:
    f.write(content)
