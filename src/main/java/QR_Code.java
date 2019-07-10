import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

class QR_Code
{
    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public void getQRCode(String email, String name, String number)
    {
        try
        {
            String text = name + " " + email + " " + number;
            String qr_code_image_path = "./QR_Codes/"+text+".png";
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 350, 350);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//            byte[] pngData = pngOutputStream.toByteArray(); 
//            return pngData;
            generateQRCodeImage(text, 350, 350, qr_code_image_path);
        }
        catch (WriterException e)
        {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}