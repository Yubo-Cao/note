from PIL import Image, ImageDraw, ImageFont

# create an image
out = Image.new("RGB", (150, 100), (255, 255, 255))

# get a drawing context
d = ImageDraw.Draw(out)

# draw multiline text
d.multiline_text((10, 10), "Hello\nWorld", fill=(0, 0, 0))
d.arc((10, 10, 50, 50), 0, 360, fill=(0, 0, 0), width=5)

out.show()