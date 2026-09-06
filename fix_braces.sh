#!/bin/bash
sed -i '/mainViewModel.speak("${post.title}. ${post.messageOriginal}")/!b;n;n;n;n;n;n;n;n;n;n;n;n;a\        }' app/src/main/java/com/example/ui/screens/AtualidadeScreen.kt
