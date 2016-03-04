import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveGame {

	public static void saveGame(Player player, WorldMap map) throws IOException {
		List<String> lines = Arrays.asList(
				"PlayerPosX:" + map.getPlayerPos()[0], 
				"PlayerPosY:" + map.getPlayerPos()[1],
				"Map:" + map.getName());
		Path file = Paths.get("saves/save-0");
		Files.write(file, lines, Charset.forName("UTF-8"));
	}

	public static ArrayList<String> loadGame() {
		ArrayList<String> saveValues = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader("saves/save-0")))
		{

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				String[] split = sCurrentLine.split(":");
				saveValues.add(split[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return saveValues; 
	}

	public static void saveGame(String string) throws IOException {
		if (string.equals("default")){
			List<String> lines = Arrays.asList(
					"PlayerPosX:" + 7, 
					"PlayerPosY:" + 5,
					"Map:" + "testmap");
			Path file = Paths.get("saves/save-0");
			Files.write(file, lines, Charset.forName("UTF-8"));
		}
		
	}

}
