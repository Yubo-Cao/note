# Bitmap
- Bitmaps are regular rectangular mesh of cells called **pixels**, each pixel containing a color value.
- Each pixel is actually a very small square that is assigned a color, and then arranged in a pattern to form the image
- ![[raster_example_ap_csp.jpg|300]]
## Color Spaces
### Additivie / RGB
- Three overlapping light bulbs in a vacuum, adding together to create white.
	- Red
	- Green
	- Blue
- Sensing, representation, and display of images.
- Device dependnet
	- Different devices detect or reproduce a given RGB values differently.
	- RGB value varies from manufacture to manufacturer, even same device overr time. 
	- **Require color management to maintain same color.**
- ![[RGB_ap_csp.png|300]]
### Subtractive / CMYK
- Three splotches of paint on white paper, subtracting together to turn the paper black.
	- Cyan = White - Red
	- Magenta = White - Green
	- Yellow = White - Blue
	- Key = Black, rather than mix cyan-magenta-yellow.
		- save cost on ink
		- produce deeper black tones
- Used in color printing, as well as describing the printing process itself.
	- ![[CMYK_ap_csp.png|300]]
### HSL/HSV
- Hue Saturation lightness/value are alternative representation of the RGB color model, designed in the 1970 by computer graphics researchers to more closely align **with human vision**
- ![[HSV_ap_csp.png|300]]
- Model description
	- Colors of each hue are arranged in a radial slice
	- Centraul axis of neutral colors to outside, saturation increases
	- Black from bottom to white to top
- Resemble
	- **Natural Color System** or **Munsell Color System**
	- placing fully saturated colors around a circle at a lightness value of $\frac{1}{2}$
	- where a lightness value of 0 or 1 is fully black or white, respectively.
## Bit Depth
- Bit depth is number of bits required to store a single pixel
	- Large bit depth can be used to represent a large number of colors
	- $\textrm{Number of colors} = 2^{\textrm{bit depth}}$, this formula can be used to determine maximum number of colors or shades that can be represented given bit depth

| Bits | Colors                | Description                                                | Common uses                                                  |
| ---- | --------------------- | --------------------------------------------------------- | ------------------------------------------------------------ |
| 1    | 2                     | Monochrome, Black and White                                | Icons & Cursors                                              |
| 8    | 256                   | Color pallette or full grayscale. 0 is black, 255 is white | Drawing, Icons, Grayscale Images                             |
| 24   | 16M                   | 16 million colors, True Color                              | Photos                                                       |
| 32   | 16M with transparency | True Color & Transparency                                  | Multiple image components can be overlaid to make composite images, this way |
### 24 Bits RGB
- Represented by 24 bits per pixels in three separate **color channels**
	- 8 bits for red
	- 8 bits for green
	- 8 bits for blue
- In each **channel**, 8 bits is used to represent 256 possible intensities. Therefore, $2^{24}$ = 16777216 different colors can be represented
- In each chanell, same is grayscale image, 0 represents no such color and 255 represents the greatest intensity of that color in the image

| R   | G   | B   | Combined Color |
| --- | --- | --- | -------------- |
| 0   | 0   | 0   | black          |
| 0   | 0   | 255 | blue           |
| 0   | 255 | 255 | cyan           |
| 0   | 255 | 0   | green          |
| 255 | 0   | 255 | magenta        |
| 255 | 0   | 0   | red            |
| 255 | 255 | 255 | white          |
| 255 | 255 | 0   | yellow         |
### 32 Bits RGB color with Transparency(RGBA)
- Some images allow transparency in addition to color.
	- Same 3 channels as 24 bits RGB
	- Additional 8 bits channel, alpha channel, to represent transparency
		- 0 means completely transparent
		- 255 means completely opaque
		- 128 means halfway transparent

# Common Raster

| Name                                                         | Extension       | Features                                                     | Compression                                                  | Bit Depth                                                    | Inventor                                                 | Metadata                                                     |
| ------------------------------------------------------------ | --------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | -------------------------------------------------------- | ------------------------------------------------------------ |
| **JPEG** -- jointed photographic expert group (compression method)<br />**JFIF** -- JPEG file interchange format (JPEG usually stored this way) | `.jpg`,`.jpeg`  | - Significant reduction of the file size.<br />- Generational degradation when repeatedly edited and saved | Lossy                                                        | - 8 bits grayscale<br />-24 bits true color                  | Jointed photographic expert group                        | - Freeform comment<br />- Prefined EXIF/IPTC field           |
| **TIFF** --  Tagged image file format                        | `.tiff`,`.tif`  | - Easily extend able<br />- Different from vendor to vendor, proprietary special purpose tags, lacks portability<br />- Widely accepted as photography file standard in printing business<br />- Device specific color spaces, CMYK | Lossy or Lossless (LZW).<br />RLE, Packbits and CCITT        | - 24 bits<br />- 48 bits                                     | Aldus (now absorbed to Adobe) and Microsoft Corporations | - Prefined text fields in 7bit ASCII<br />- EXIF/IPTC metadata |
| **GIF** - Graphics interchange format                        | `.gif`          | - Graphics with **few colors** is suitable, simple diagrams, shapes, logos, cartoon<br />- Bad for **dithered** or **photographic** | Lossless (LZW)                                               | - 8 bit palette, 256 color only                              | CompuServe                                               | Free-form comments in 7-bit ASCII                            |
| **BMP** - Windows bitmap                                     | `.bmp`          | - Simple structure and wide acceptance<br />- Large          | Typically, uncompressed<br />RLE in 16 color and 256 color modes. Monochrome in 16 color is better deal. | - 1,2,4,8,16,24,32 bits                                      | Microsoft                                                | False                                                        |
| **PNG** -  Portable network graphics                         | `.png`          | - Free, open-source alternative to GIF<br />- Better for image with large, uniformly colored areas<br />- Able to handle photograph and suit browsers<br />- **Progressive display**<br />- **Robust and file integrity check**<br />- Simple detection of common transmission error | Lossless compression (Deflate)                               | - 8 bit palette (grayscale) and/or alpha <br />- 24 bits true color and/or alpha<br />- 48 bit true color and/or alpha | PNG Development Group, donated to W3C                    | K,V pairs<br />Poor support of EXIF                          |
| **PCX** - Picture exchange                                   | `.pcx`, `.dcx`  | - `@Deprecated`, no clear advantage in term of file size<br />- Used to be dominant with Ms-DOS | Lossless (RLE)                                               | - 8-bit grayscale, <br />- 1-bit black<br />- indexed color images | ZSoft                                                    | False                                                        |
| **TGA** - Truevision TGA                                     | `.tga`, `.tpic` | - `@Deprecated`, no clear advantage in term of file size     | Lossless (RLE/Packbits similar)                              | - 8, 15, 16, 24, or 32 bits <br />- 4 color modes and 8 color modes (for portability, 16 color mode) | Truevision                                               | Prefined text fields in ASCII<br />Poor support of EXIF      |

# Vector Formats

- Vector image formats contain a geometric description which can be rendered smoothly at any desired display size
- Vector graphics must be rasterized in order to be displayed on digital monitors
  - Analog CRT
  - electronic test equipment
  - Medical montiors
  - etc.
- Smaller
- Edited by Adobe Illustrator etc.
- More scalable than bitmap images, never pixel like and always crisp.
- ![[vector_ap_csp.jpg|300]]
## SVG

- Scalable Vector graphics, `.svg`
- World Wide Web Consortium, W3C created it and developed it
- Versatile, scriptable, and all purpose vector format for the web and otherwise
- No compression scheme, but since it is just xml, any program can compress it

# Compression

- **Lossless** 
  - Reduce the file size while preserving a perfect copy of the original uncompressed image
  - Generally, but not always results in larger files than lossy
  - Avoid accumulating stages of re-compression when editing images
- **Lossy**
  - Preserve a representation of the original uncompressed image, appear **to be perfect**, but it is not
  - Achieve smaller file sizes
  - **Variable** compression that trades image quality for file size

## Image Compression Algorithms

- **None/Raw**
  - Raw bits are stored exactly or very close to the way they appear on the screen
- **RLE(Run length encoding)**
  - Detect continuous run of a single color and packs it in a tuple of `(color, frequency)`
  - `TIFF` support two alternative RLE
    - **Packbits**
    - **CCITT** Only available for black and white images
  - Works well for images with long contiguous run of a single color in horizontal direction
  - **Monochrome** or **Low color drawings** only. Bad for photos
- **LZW(Lempel-Ziv)**
  - Adaptive algorithm that looks for repetitive horizontal runs, **effectively pack other types of repetition**
  - Learns the image as it compress it.
  - Once it finds a repeated location, it outputs a reference to the repetition rather then saving the same pixels again
  - Both **TIFF** and **GIF** support it, but GIF do a better job for small images with a low bit depth
- **Deflate**
  - Compression algorithm employed by PNG.
  - Use LZ77 encoded with Huffman code. Filter step before the actual compression make it more efficient, which does not modify image, but convert them to pixels that would compress better. 
  - PNG superior to LZW compressed format
- **JPEG**
  - Lossy compression for photos
  - Color and graysale, both. Apply it to non-photography will result low quality and big file, this include drawing

# Metadata and Extensibility

- Metadata is some informations about the image
- Textual metadata
  - Author name
  - Image title
  - Camara model
  - Application name
  - etc.
- Numeric metadata
  - Exposure time
  - Resolution
  - Orientation
  - etc.
  - **EXIF** is a comprehensive set of metadata fields for photos
- 
