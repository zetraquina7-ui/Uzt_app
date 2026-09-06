import urllib.request
import re

missing = ["JESIHHP0iqE", "S5gzoD269E4", "qd0DxrOngz4", "_SVda7zfTx8", "tPgO6lxf0pM", "5U-cinIL2uY", "wnr1gdlgJQk"]

for vid in missing:
    try:
        html = urllib.request.urlopen("https://www.youtube.com/watch?v=" + vid).read().decode('utf-8')
        title = re.search(r'<title>(.*?)</title>', html).group(1).replace(" - YouTube", "")
        print(f'YouTubeVideoTrack("{vid}", "{title}", "{vid}", "https://i3.ytimg.com/vi/{vid}/hqdefault.jpg", "Educativo", "📖"),')
    except Exception as e:
        pass
