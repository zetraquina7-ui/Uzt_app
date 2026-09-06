#!/bin/bash
cat << 'INNER_EOF' > script.sed
/var recognizer = speechRecognizer/c\
                // Always create a fresh SpeechRecognizer to avoid silent hang bugs on real devices\
                try {\
                    speechRecognizer?.destroy()\
                } catch (e: Exception) {}\
                var recognizer: SpeechRecognizer? = null\
                if (!isPreviewMode) {\
                    try {\
                        if (SpeechRecognizer.isRecognitionAvailable(context)) {\
                            recognizer = SpeechRecognizer.createSpeechRecognizer(context)\
                            speechRecognizer = recognizer\
                        }\
                    } catch (e: Throwable) {\
                        Log.e("LiveAvatar", "SpeechRecognizer creation failed", e)\
                    }\
                }
/if (recognizer == null && !isPreviewMode) {/,/}/d
INNER_EOF
sed -i -f script.sed ./app/src/main/java/com/example/ui/components/ZeLiveAvatarComponent.kt
