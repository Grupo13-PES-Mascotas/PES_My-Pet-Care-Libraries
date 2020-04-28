# Pes_My-Pet-Care-Libraries
A software project from FIB (Facultat d'Informàtica de Barcelona)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e4eba67e1aca4bc6853f7ce45475a3df)](https://www.codacy.com/gh/Grupo13-PES-Mascotas/PES_My-Pet-Care-API?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Grupo13-PES-Mascotas/PES_My-Pet-Care-API&amp;utm_campaign=Badge_Grade)	[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/e4eba67e1aca4bc6853f7ce45475a3df)](https://www.codacy.com/gh/Grupo13-PES-Mascotas/PES_My-Pet-Care-API?utm_source=github.com&utm_medium=referral&utm_content=Grupo13-PES-Mascotas/PES_My-Pet-Care-API&utm_campaign=Badge_Coverage)	[![Release](https://jitpack.io/v/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries.svg)](https://jitpack.io/#Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries)![](https://github.com/Grupo13-PES-Mascotas/Pes_My-Pet-Care-Libraries/workflows/Build/badge.svg?event=pull_request)

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
* [Usermanager Javadoc](https://jitpack.io/com/github/Grupo13-PES-Mascotas/PES_My-Pet-Care-Libraries/v1.1.0/javadoc)
