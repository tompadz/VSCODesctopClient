package utils

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam

class ImageUtil {

    //TODO:"Preserving aspect ratio of images"

    fun compressImage(bytes:ByteArrayInputStream, compressValue:Float = 0.5f): ByteArray {
        val image : BufferedImage = ImageIO.read(bytes)
        return compressImage(image, compressValue)
    }

    fun compressImageAndResize(
        bytes:ByteArrayInputStream,
        width:Int,
        height: Int,
        compressValue:Float = 0.5f
    ): ByteArray {
        val image = resizeImage(bytes, width, height)
        return compressImage(image, compressValue)
    }

    fun resizeImage(bytes:ByteArrayInputStream, width:Int, height:Int): BufferedImage {
        val image : BufferedImage = ImageIO.read(bytes)
        val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics2D: Graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null)
        graphics2D.dispose()
        return resizedImage
    }

    private fun compressImage(image:BufferedImage, compressValue: Float): ByteArray {
        val jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next()
        val jpgWriteParam = jpgWriter.defaultWriteParam
        jpgWriteParam.compressionMode = ImageWriteParam.MODE_EXPLICIT
        jpgWriteParam.compressionQuality = compressValue

        val bos = ByteArrayOutputStream()
        val ios = ImageIO.createImageOutputStream(bos)
        jpgWriter.output = ios

        val outputImage = IIOImage(image, null, null)
        jpgWriter.write(null, outputImage, jpgWriteParam)
        val result = bos.toByteArray()
        jpgWriter.dispose()
        return result
    }
}