pluginManagement {
    repositories {
//        mavenLocal()

//        maven {
//            setUrl("https://developer.huawei.com/repo")
//            content {
//                includeGroupByRegex("com\\.huawei\\..*")
//            }
//        }

        maven { setUrl("https://mirrors.tencent.com/nexus/repository/maven-public") }

        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "LogCTRL"