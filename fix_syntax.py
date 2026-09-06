import os
import re

filepath = "/app/applet/app/src/main/java/com/example/ui/screens/LearnScreen.kt"
with open(filepath, "r") as f:
    content = f.read()

# Let's revert the replacements and do it properly.
# The bad string: "gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))\n                )\n                }\n                Spacer(modifier = Modifier.height(10.dp))"
# We want to change the extra closing braces back to original EXCEPT for the first one that matches the header.
# Actually, I'll just find every occurrence of the bad string and change it back.

bad_str = """                    gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                )
                }
                Spacer(modifier = Modifier.height(10.dp))"""
good_str = """                    gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                )
                Spacer(modifier = Modifier.height(10.dp))"""

content = content.replace(bad_str, good_str)

# Now, we manually add the closing brace only after the actual ScreenHeader
# Let's find:
target_header = """                if (showHeader) {
                    ScreenHeader(
                        title = "Escola Mágica",
                    subtitle = "Aprende com o Zé Traquina",
                    icon = "📚",
                    gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                )
                Spacer"""
fixed_header = """                if (showHeader) {
                    ScreenHeader(
                        title = "Escola Mágica",
                        subtitle = "Aprende com o Zé Traquina",
                        icon = "📚",
                        gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                    )
                }
                Spacer"""
content = content.replace(target_header, fixed_header)

with open(filepath, "w") as f:
    f.write(content)

