# Pes_My-Pet-Care-Libraries
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e4eba67e1aca4bc6853f7ce45475a3df)](https://www.codacy.com/gh/Grupo13-PES-Mascotas/PES_My-Pet-Care-API?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Grupo13-PES-Mascotas/PES_My-Pet-Care-API&amp;utm_campaign=Badge_Grade)	[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/e4eba67e1aca4bc6853f7ce45475a3df)](https://www.codacy.com/gh/Grupo13-PES-Mascotas/PES_My-Pet-Care-API?utm_source=github.com&utm_medium=referral&utm_content=Grupo13-PES-Mascotas/PES_My-Pet-Care-API&utm_campaign=Badge_Coverage)	[![Release](https://jitpack.io/v/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries.svg)](https://jitpack.io/#Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries) ![Build](https://github.com/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries/workflows/Build/badge.svg)

A software project from FIB (Facultat d'Informàtica de Barcelona
## Download

### Gradle:
Add to your project build.gradle:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
To add a specific module:

```gradle
dependencies {
	implementation 'com.github.Grupo13-PES-Mascotas.PES_My-Pet-Care-Libraries:{module}:{latest version}'
}
```
To add all modules:
```gradle
dependencies {
	implementation 'com.github.Grupo13-PES-Mascotas:PES_My-Pet-Care-Libraries:{latest version}'
}
```

### Maven:
Add to your Maven build:
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
  </repositories>
```
To add specific module:
```xml
<dependency>
     <groupId>com.github.Grupo13-PES-Mascotas.PES_My-Pet-Care-Libraries</groupId>
     <artifactId>{module}</artifactId>
     <version>{latest version}</version>
</dependency>
```
To add all modules:
```xml
<dependency>
     <groupId>com.github.Grupo13-PES-Mascotas</groupId>
     <artifactId>PES_My-Pet-Care-Libraries</artifactId>
     <version>{latest version}</version>
</dependency>
```
## Documentation
Documentation for the current release:

* [Usermanager Javadoc](https://javadoc.jitpack.io/com/github/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries/usermanager/v2.0.0/javadoc)

* [Communitymanager Javadoc](https://javadoc.jitpack.io/com/github/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries/communitymanager/v2.0.0/javadoc)
			     
* [Httptools Javadoc](https://javadoc.jitpack.io/com/github/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries/httptools/v2.0.0/javadoc)

Both Usermanager and Communitymanager libraries need Httptools to work.
