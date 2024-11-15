

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class YouTubeDownloader {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String downloadPath = "C:\\Users\\Ronald\\Downloads\\VS CODE\\Python\\sonidosXD";  // Ruta donde se guardará el audio

        while (true) {
            System.out.println("Ingrese la URL del video:");
            String url = sc.nextLine();
            descargarAudio(url, downloadPath);  // Pasar la ruta al método
        }
    }

    public static void descargarAudio(String url, String downloadPath) {
        // Comando para descargar solo el audio en formato MP3 y especificar la ruta
        String command = "yt-dlp -x --audio-format mp3 -o \"" + downloadPath + "/%(title)s.%(ext)s\" " + url;

        try {
            // Ejecutar el comando
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            // Leer la salida del comando
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Audio descargado con éxito en formato MP3.");
            } else {
                System.out.println("Error al descargar el audio. Código de salida: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
