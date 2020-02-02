/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import it.unitn.disi.wp.project.hms.commons.configs.QRProps;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Generatore di QR CODE in uno dei seguenti oggetti:
 * <ul
 *     <li>{@link BufferedImage}</li>
 *     <li>{@link java.util.Base64}.toString()</li>
 * </ul>
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 31.12.2019
 */
public class QRGen {

    static private final int DEFAULT_WIDTH = 1200;
    static private final int DEFAULT_HEIGHT = 1200;
    static private final String DEFAULT_EXTENSION = QRProps.getExtension();

    static private QRCodeWriter qrCodeWriter = new QRCodeWriter();

    private QRGen() {
        super();
    }

    static public BufferedImage getBufferedImage(String contents, int width, int height) throws WriterException {
        BitMatrix bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    static public BufferedImage getBufferedImage(String contents) throws WriterException {
        return getBufferedImage(contents, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    static public String getBase64Binary(String contents, int width, int height, String extension) throws WriterException {
        String ext = extension;
        if(extension.contains(".") || extension.charAt(0)=='.')
            ext = extension.substring(1);

        byte[] imageData;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(QRGen.getBufferedImage(contents,width,height), ext, baos);
            imageData = baos.toByteArray();
        } catch (WriterException | IOException e) {
            throw new WriterException(e);
        } finally {
            if(baos!=null) {
                try {
                    baos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return DatatypeConverter.printBase64Binary(imageData);
    }

    static public String getBase64Binary(String contents, String extension) throws WriterException {
        return getBase64Binary(contents,DEFAULT_WIDTH,DEFAULT_HEIGHT,extension);
    }

    static public String getBase64Binary(String contents, int width, int height) throws WriterException {
        return getBase64Binary(contents, width, height, DEFAULT_EXTENSION);
    }

    static public String getBase64Binary(String contents) throws WriterException {
        return getBase64Binary(contents,DEFAULT_WIDTH,DEFAULT_HEIGHT,DEFAULT_EXTENSION);
    }

}
