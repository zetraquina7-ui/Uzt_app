import urllib.request
import re

video_ids = [
    "fe4HmhQRCUg", "FunPMvy6He8", "jYYvwC3L2kI", "Cdys2zuYpVs", "GXDSVN0nfJo", 
    "JT5dhPkaXLI", "Ce6QVBTkSUI", "VbUX6EODgc0", "npjny0rOVok", "Xkbtbam05_w", 
    "hrpeW39DYTM", "DhvP0v6uZEI", "pmMVHEF0zQg", "a_Ole8Cbl9M", "wOnvZxQ-Iio"
]

for vid in video_ids:
    try:
        html = urllib.request.urlopen("https://www.youtube.com/watch?v=" + vid).read().decode('utf-8')
        title = re.search(r'<title>(.*?)</title>', html).group(1).replace(" - YouTube", "")
        print(f'YouTubeVideoTrack("{vid}", "{title}", "{vid}", "https://i3.ytimg.com/vi/{vid}/hqdefault.jpg", "Música", "🎵"),')
    except Exception as e:
        print(vid, e)
