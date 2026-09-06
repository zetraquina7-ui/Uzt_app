import urllib.request
import re

playlists = {
    "PLHz1Xt0IaQWM": "Música",
    "PLT7ZV5QsDKA4": "Educativo",
    "PLHXyMYX6Yxxc": "Diversão"
}

for pid, name in playlists.items():
    print(f"--- Playlist: {name} ({pid}) ---")
    try:
        html = urllib.request.urlopen(f"https://www.youtube.com/playlist?list={pid}").read().decode('utf-8')
        # find all watch?v=...
        video_ids = re.findall(r'watch\?v=([a-zA-Z0-9_-]{11})', html)
        # remove duplicates while preserving order
        unique_vids = list(dict.fromkeys(video_ids))
        for vid in unique_vids:
            try:
                vhtml = urllib.request.urlopen("https://www.youtube.com/watch?v=" + vid).read().decode('utf-8')
                title = re.search(r'<title>(.*?)</title>', vhtml).group(1).replace(" - YouTube", "")
                print(f'YouTubeVideoTrack("{vid}", "{title}", "{vid}", "https://i3.ytimg.com/vi/{vid}/hqdefault.jpg", "{name}", "▶️"),')
            except Exception as e:
                pass
    except Exception as e:
        print(f"Failed to fetch playlist {pid}: {e}")
