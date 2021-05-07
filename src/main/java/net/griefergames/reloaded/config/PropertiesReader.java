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

    private final String fileName = "datasource.properties";

    private Properties datasourceProperties;

    public void loadDataSourceProperties() {
        this.createDataSourceProperties();

        datasourceProperties = new Properties();
        try ( BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( this.fileName ) ) ) {
            datasourceProperties.load( inputStream );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while loading '" + this.fileName + "'" );
        }
    }

    private void createDataSourceProperties() {
        try {
            final File file = new File( this.fileName );
            if ( file.createNewFile() ) {
                this.fillFile( file );
                System.out.println( "File successfully created" );
                return;
            }

            System.out.println( "File does already exists" );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while creating '" + this.fileName + "'" );
        }
    }

    private void fillFile( final File file ) {
        try ( FileWriter fileWriter = new FileWriter( file ); InputStream inputStream = this.getFileFromResourceAsStream( this.fileName ) ) {
            for ( String output : this.getConfigInput( inputStream ) )
                fileWriter.write( output + System.lineSeparator() );
        } catch ( IOException exception ) {
            ExceptionHandler.handleException( exception, "Error while filling '" + this.fileName + "'" );
        }
    }

    private InputStream getFileFromResourceAsStream( final String fileName ) {
        final ClassLoader classLoader = GrieferGamesReloaded.class.getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream( fileName );
        if ( inputStream == null )
            throw new IllegalArgumentException( "InputStream is null! " + fileName );
        else
            return inputStream;
    }

    private List<String> getConfigInput( final InputStream inputStream ) {
        final List<String> inputList = new ArrayList<>();

        try ( InputStreamReader streamReader = new InputStreamReader( inputStream, StandardCharsets.UTF_8 ); BufferedReader reader = new BufferedReader( streamReader ) ) {
            reader.lines().forEach( inputList::add );
        } catch ( IOException exception ) {
            exception.printStackTrace();
        }

        return inputList;
    }

}