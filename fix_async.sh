cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp
                AsyncImage(
                    model = R.drawable.img_app_icon_1785061192574,
                    contentDescription = "Avatar do Zé Traquina",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().padding(2.dp).clip(CircleShape)
                )
INNER_EOF
sed -i -e '/Image(/,/) /{r app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp' -e 'd}' app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
