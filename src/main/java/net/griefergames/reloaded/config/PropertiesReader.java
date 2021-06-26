package net.griefergames.reloaded.config;

import lombok.Getter;
import lombok.val;
import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.exception.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Getter
public class PropertiesReader {
    private final Properties dataSourceProperties;
    private final String fileName;

    public PropertiesReader() {
        this.dataSourceProperties = new Properties();
        this.fileName = GrieferGamesReloaded.PLUGIN.getPlugin().getDataFolder().getAbsolutePath() + "\\" + "datasource.properties";
    }

    public void loadDataSourceProperties() {
        this.createDataSourceProperties();

        try (val inputStream = new BufferedInputStream(new FileInputStream(this.fileName))) {
            dataSourceProperties.load(inputStream);
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Error while loading '" + this.fileName + "'", true);
        }
    }

    private void createDataSourceProperties() {
        try {
            val file = new File(this.fileName);
            if (file.createNewFile()) {
                if (file.mkdirs()) {
                    this.fillFile(file);
                    System.out.println("File successfully created");
                }
                return;
            }

            System.out.println("File does already exists");
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Error while creating '" + this.fileName + "'", true);
        }
    }

    private void fillFile(@NotNull final File file) {
        try (val fileWriter = new FileWriter(file); val inputStream =
                this.getFileFromResourceAsStream(this.fileName)) {
            assert inputStream != null;
            for (val output : this.getConfigInput(inputStream))
                fileWriter.write(output + System.lineSeparator());
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Error while filling '" + this.fileName + "'", true);
        }
    }

    private InputStream getFileFromResourceAsStream(@NotNull final String fileName) {
        val classLoader = GrieferGamesReloaded.class.getClassLoader();
        try (val inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null)
                throw new IllegalArgumentException("InputStream is null! " + fileName);
            else
                return inputStream;
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Inputstream is null", true);
        }

        return null;
    }

    private List<String> getConfigInput(@NotNull final InputStream inputStream) {
        @NotNull final List<String> inputList = new ArrayList<>();

        try (val streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8); val reader = new BufferedReader(streamReader)) {
            reader.lines().forEach(inputList::add);
        } catch (IOException exception) {
            ExceptionHandler.handleException(exception, "Stream or Buffer reader is null", true);
        }

        return inputList;
    }
}