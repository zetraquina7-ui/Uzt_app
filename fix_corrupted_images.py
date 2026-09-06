import os

replacements = {
    "R.drawable.img_header_ze_new": "R.drawable.img_header_universo",
    "R.drawable.header_pixar_polo_verde": "R.drawable.img_header_universo",
    "R.drawable.img_header_escola_pt": "R.drawable.img_header_universo",
    "R.drawable.img_ze_fullscreen_bg": "R.drawable.img_main_background",
    "R.drawable.img_ze_real_mascot_transparent": "R.drawable.img_ze_mascot_transp",
    "R.drawable.header_ze_traquina_3d": "R.drawable.img_header_universo",
    "R.drawable.img_app_icon": "R.drawable.ic_ze_traquina_launcher",
    "R.drawable.img_header_real": "R.drawable.img_header_universo",
    "R.drawable.ze_ai_mascot_transparent_left": "R.drawable.img_ze_mascot_transp",
    "R.drawable.img_learn_ze": "R.drawable.img_header_universo",
    "R.drawable.img_ze_face_beret": "R.drawable.img_ze_face_beret_alt",
    "R.drawable.img_hero_banner_front": "R.drawable.img_main_background",
    "R.drawable.img_diff_farm": "R.drawable.img_diff_space",
    "R.drawable.hero_banner_pixar": "R.drawable.img_header_universo",
    "R.drawable.header_games_ze_traquina": "R.drawable.img_header_universo",
    "R.drawable.img_header_escola_magica": "R.drawable.img_header_universo",
    "R.drawable.img_hero_banner_wide": "R.drawable.img_main_background",
    "R.drawable.img_ze_app_bg": "R.drawable.img_main_background",
    "R.drawable.img_hero_banner_ze_char": "R.drawable.img_main_background",
    "R.drawable.img_header_learn": "R.drawable.img_header_universo",
    "R.drawable.img_header_escola_pt_v2": "R.drawable.img_header_universo",
    "R.drawable.img_hero_banner": "R.drawable.img_main_background",
    "R.drawable.hero_green_polo": "R.drawable.ze_mascot_green_polo",
    "R.drawable.img_ze_mascot_polo": "R.drawable.ze_mascot_green_polo",
    "R.drawable.img_header_home_alt": "R.drawable.img_header_universo",
    "R.drawable.ic_spot_diff_modified": "R.drawable.ic_spot_diff_base",
    "R.drawable.bg_ze_traquina": "R.drawable.img_header_universo",
    "R.drawable.img_header_home": "R.drawable.img_header_universo",
    "R.drawable.ze_mascot_pixar": "R.drawable.ze_mascot_green_polo",
    "R.drawable.puzzle_bubble_ui": "R.drawable.img_main_background",
    "R.drawable.img_ze_mascot_final": "R.drawable.img_ze_mascot_transp",
}

for root, _, files in os.walk('app/src/main/java'):
    for file in files:
        if file.endswith('.kt') or file.endswith('.tmp'):
            path = os.path.join(root, file)
            with open(path, 'r') as f:
                content = f.read()
            original = content
            for k, v in replacements.items():
                content = content.replace(k, v)
            if content != original:
                with open(path, 'w') as f:
                    f.write(content)
                print(f"Updated {path}")
