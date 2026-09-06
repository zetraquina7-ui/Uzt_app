sed -i 's/val isEmulator: Boolean = false/val isEmulator: Boolean by lazy {/g' app/src/main/java/com/example/util/EmulatorUtils.kt
sed -i 's/val _ignored: Boolean by lazy {//g' app/src/main/java/com/example/util/EmulatorUtils.kt
