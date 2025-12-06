plugins {
	id("mod-platform")
	id("fabric-loom")
	id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
	kotlin("jvm") version "2.2.10"
	id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

platform {
	loader = "fabric"
	dependencies {
		required("minecraft") {
			versionRange = prop("deps.minecraft").replace("pre", "beta.")
		}
		required("fabric-api") {
			slug("fabric-api")
			versionRange = ">=${prop("deps.fabric-api")}"
		}
		required("fabricloader") {
			versionRange = ">=${libs.fabric.loader.get().version}"
		}
		required("fzzy_config") {
			slug("fzzy-config")
			versionRange = "*"
		}
		required("accessories") {
			slug("accessories")
			versionRange = "*"
		}
		optional("modmenu") {
			slug("modmenu")
		}
		optional("lambdynlights") {
			slug("lambdynamiclights")
		}
	}
}

loom {
	runs.named("client") {
		client()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "client"
		programArgs("--username=Dev")
		configName = "Fabric Client"
	}
	runs.named("server") {
		server()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "server"
		configName = "Fabric Server"
	}
}

stonecutter {
	val dir1 = eval(current.version, ">1.21.10")
	replacements.string {
		direction = dir1
		replace("ValidatedIdentifier", "ValidatedIdentifier")
	}
	replacements.string {
		direction = dir1
		replace("ResourceLocation", "Identifier")
	}
	val dir2 = eval(current.version, ">=1.21.10")
	replacements.string {
		direction = dir2
		replace("ExpandedSimpleContainer ", "ExpandedContainer ")
	}
	replacements.string {
		direction = dir2
		replace("io.wispforest.accessories.impl.ExpandedSimpleContainer", "io.wispforest.accessories.impl.core.ExpandedContainer")
	}
	replacements.string {
		direction = dir2
		replace("io.wispforest.accessories.api.Accessory", "io.wispforest.accessories.api.core.Accessory")
	}
	replacements.string {
		direction = dir2
		replace("io.wispforest.accessories.api.AccessoryRegistry", "io.wispforest.accessories.api.core.AccessoryRegistry")
	}
	replacements.string {
		direction = dir2
		replace("io.wispforest.accessories.api.client.AccessoryRenderer", "io.wispforest.accessories.api.client.renderers.AccessoryRenderer")
	}
	replacements.string {
		direction = dir2
		replace("io.wispforest.accessories.api.client.SimpleAccessoryRenderer", "io.wispforest.accessories.api.client.renderers.SimpleAccessoryRenderer")
	}
}

fletchingTable {
	mixins.create("main") {
		mixin("default", "${prop("mod.id")}.mixins.json")
	}
}

repositories {
	maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
	maven("https://maven.fzzyhmstrs.me/") { name = "Fzzy Config" }
	maven("https://maven.wispforest.io/releases/") { name = "Wisp Forest" }
	maven("https://maven.terraformersmc.com/" ) { name = "TerraformersMC" }
	maven("https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
	maven("https://maven.shedaniel.me/") { name = "Shedaniel" }
	maven("https://jitpack.io") { name = "Jitpack" }
	exclusiveContent {
		forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
		filter { includeGroup("maven.modrinth") }
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
	@Suppress("UnstableApiUsage")
	mappings(
		loom.layered {
			officialMojangMappings()
			if (hasProperty("deps.parchment")) parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
		})
	modImplementation(libs.fabric.loader)
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric-api")}")
	modImplementation("me.fzzyhmstrs:fzzy_config:${prop("deps.fzzy_config")}")
	modImplementation("com.terraformersmc:modmenu:${prop("deps.modmenu")}")
	implementation("com.moulberry:mixinconstraints:${prop("deps.mixinconstraints")}")
	include("com.moulberry:mixinconstraints:${prop("deps.mixinconstraints")}")
	modImplementation("io.wispforest:accessories-fabric:${prop("deps.accessories")}") {
		exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
	}
	modImplementation("io.wispforest:owo-lib:${prop("deps.owo")}") {
		exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
	}

	// Supported mods
	modCompileOnly("maven.modrinth:serene-seasons:${prop("deps.ss")}") {
		exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
	}
	modCompileOnly("maven.modrinth:raised:${prop("deps.raised")}") {
		exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
	}
	modCompileOnly("maven.modrinth:notes:${prop("deps.notes")}") {
		exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
	}
	// 1.20.1 only mods
	if (stonecutter.eval(stonecutter.current.version, "1.20.1")) {
		modCompileOnly("maven.modrinth:sodium-dynamic-lights:${prop("deps.sdl")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
	}
	// 1.21.1 only mods
	if (stonecutter.eval(stonecutter.current.version, "1.21.1")) {
		modCompileOnly("maven.modrinth:farmers-delight-refabricated:${prop("deps.fdrf")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
		modCompileOnly(rootProject.files("ext_imports/citresewn-defaults-1.2.2+1.21.jar"))
		modCompileOnly("maven.modrinth:cit-resewn:${prop("deps.cit")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
	}
	// 1.20.1-1.21.1 only mods
	if (stonecutter.eval(stonecutter.current.version, ">= 1.20.1 <= 1.21.1")) {
		modCompileOnly("maven.modrinth:extrasoundsforge:${prop("deps.extrasounds")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
		modCompileOnly("maven.modrinth:aileron:${prop("deps.aileron")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
		modCompileOnly("maven.modrinth:fabric-seasons:${prop("deps.fs")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
		modCompileOnly("maven.modrinth:fabric-seasons-extras:${prop("deps.fsextra")}") {
			exclude(group = "net.fabricmc.fabric-api", module = "fabric-api")
		}
	}
}
