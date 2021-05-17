package net.griefergames.reloaded.config;

import lombok.Getter;
import net.griefergames.reloaded.GrieferGamesReloaded;
import net.griefergames.reloaded.exception.ExceptionHandler;

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

        try ( final var inputStream = new BufferedInputStream( new FileInputStream( this.fileName ) ) ) {
            dataSourceProperties.load( inputStream );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while loading '" + this.fileName + "'", true );
        }
    }

    private void createDataSourceProperties() {
        try {
            final var file = new File( this.fileName );
            if ( file.createNewFile() ) {
                if ( file.mkdirs() ) {
                    this.fillFile( file );
                    System.out.println( "File successfully created" );
                }
                return;
            }

            System.out.println( "File does already exists" );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while creating '" + this.fileName + "'", true );
        }
    }

    private void fillFile( final File file ) {
        try ( final var fileWriter = new FileWriter( file ); final var inputStream = this.getFileFromResourceAsStream( this.fileName ) ) {
            for ( final var output : this.getConfigInput( inputStream ) )
                fileWriter.write( output + System.lineSeparator() );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while filling '" + this.fileName + "'", true );
        }
    }

    private InputStream getFileFromResourceAsStream( final String fileName ) {
        final var classLoader = GrieferGamesReloaded.class.getClassLoader();
        try ( final var inputStream = classLoader.getResourceAsStream( fileName ) ) {
            if ( inputStream == null )
                throw new IllegalArgumentException( "InputStream is null! " + fileName );
            else
                return inputStream;
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Inputstream is null", true );
        }

        return null;
    }

    private List<String> getConfigInput( final InputStream inputStream ) {
        final List<String> inputList = new ArrayList<>();

        try ( final var streamReader = new InputStreamReader( inputStream, StandardCharsets.UTF_8 ); final var reader = new BufferedReader( streamReader ) ) {
            reader.lines().forEach( inputList::add );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Stream or Buffer reader is null", true );
        }

        return inputList;
    }

}