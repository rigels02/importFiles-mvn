# Console Java application importFiles

Imports sdf files and generates output file in required format.
Uses **chemaxon.formats.MolImporter** class
 
[https://docs.chemaxon.com/display/docs/Reading+Molecules+From+a+File](https://docs.chemaxon.com/display/docs/Reading+Molecules+From+a+File) 

## How does it work?

The main parser class is Parser_v2.java.

It uses MolImport() to get properties from input file.

Using 'CATALOG_NUMBER' property it gets defined FileType.

The FileType is used to get proper Parser instance (Parser_v2Tx) from FileTypeParserFactoryV2.java.

The rest of task is done by parser Parser_v2Tx.(Parser_v2T1, Parser_v2T2, Parser_v2T3, Parser_v2T4, Parser_v2T5,)

The result of list of records (Rec) is passed to OutputFormater.outPrint() to get output report.

## Notes

### Build as eclipse project

- To build as eclipse project the chemaxon JChem Base jar libraries must be downloaded in local directory.
- added as external Jar files in project Build Path configuration.
- exported as executable Jar by using configured Run Configuration.
- the src/main/resources/log4j.properties file must be copied into exported jar file's folder.

### Build as Maven project

- Chemaxon JChem Base jar libraries must be installed in local Maven repository.
- add Chemaxon JChem Base jars dependencies in pom.xml
- mvn clean install

### Build as Maven project with access to remote repository

Reference: [https://docs.chemaxon.com/display/docs/Introduction+for+Java+applications](https://docs.chemaxon.com/display/docs/Introduction+for+Java+applications)

Alternatively, you can reference the ChemAxon Public Repository in your Maven project's pom.xml:

~~~
<repositories>
    <repository>
        <id>ChemAxon Public Repository</id>
        <url>https://hub.chemaxon.com/artifactory/libs-release</url>
    </repository>
</repositories>
 
<dependencies>
    <dependency>
        <groupId>com.chemaxon</groupId>
        <artifactId>jchem-main</artifactId>
        <version>17.7.0</version>
    </dependency>
</dependencies>
~~~
Then add your account info in settings.xml:

~~~
<servers>
  <server>
    <id>ChemAxon Public Repository</id>
    <username>Your username</username>
    <password>Your password</password>
  </server>
</servers>
~~~
'Your password' is generated API key.

For this application it is enough to include in pom.xml following dependencies:

~~~
<dependency>
    <groupId>com.chemaxon</groupId>
    <artifactId>core</artifactId>
    <version>18.28.0</version>
        </dependency>
        
<dependency>
    <groupId>com.chemaxon</groupId>
    <artifactId>io-all</artifactId>
    <version>18.28.0</version>
</dependency>        
~~~ 

## Run maven built application

- mvn clean install
- go to 'target' directory

Run:

~~~
java -jar importFiles-jar-with-dependencies.jar ..\src\test\data1 [output file]
~~~