plugins {
	id("mod-platform")
	id("net.neoforged.moddev")
	id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
	kotlin("jvm") version "2.2.10"
	id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

platform {
	loader = "neoforge"
	dependencies {
		required("minecraft") {
			forgeVersionRange = "[${prop("deps.minecraft")}]"
		}
		required("neoforge") {
			forgeVersionRange = "[1,)"
		}
		required("fzzy_config") {
			slug("fzzy-config")
			forgeVersionRange = "[0,)"
		}
		required("accessories") {
			slug("accessories")
			forgeVersionRange = "[0,)"
		}
		optional("lambdynlights") {
			slug("lambdynamiclights")
		}
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

neoForge {
	version = property("deps.neoforge") as String
	validateAccessTransformers = true

	if (hasProperty("deps.parchment")) parchment {
		val (mc, ver) = (property("deps.parchment") as String).split(':')
		mappingsVersion = ver
		minecraftVersion = mc
	}

	runs {
		register("client") {
			client()
			gameDirectory = file("run/")
			ideName = "NeoForge Client (${stonecutter.active?.version})"
			programArgument("--username=Dev")
		}
		register("server") {
			server()
			gameDirectory = file("run/")
			ideName = "NeoForge Server (${stonecutter.active?.version})"
		}
	}

	mods {
		register(property("mod.id") as String) {
			sourceSet(sourceSets["main"])
		}
	}
}

repositories {
	maven("https://maven.parchmentmc.org") { name = "ParchmentMC" }
	maven("https://maven.fzzyhmstrs.me/") { name = "Fzzy Config" }
	maven("https://maven.wispforest.io/releases/") { name = "Wisp Forest" }
	maven("https://maven.su5ed.dev/releases") { name = "Su5ed" }
	maven("https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
	maven("https://jitpack.io") { name = "Jitpack" }
	exclusiveContent {
		forRepository { maven("https://api.modrinth.com/maven") { name = "Modrinth" } }
		filter { includeGroup("maven.modrinth") }
	}
}

dependencies {
	implementation( "me.fzzyhmstrs:fzzy_config:${prop("deps.fzzy_config")}+neoforge")
	implementation("com.moulberry:mixinconstraints:${prop("deps.mixinconstraints")}")
	jarJar("com.moulberry:mixinconstraints:${prop("deps.mixinconstraints")}")
	implementation("io.wispforest:accessories-neoforge:${prop("deps.accessories")}")
	implementation("io.wispforest:owo-lib-neoforge:${prop("deps.owo")}")

	// Supported mods
	runtimeOnly("maven.modrinth:lambdynamiclights:${prop("deps.ldl")}")
	compileOnly("maven.modrinth:serene-seasons:${prop("deps.ss")}")
	compileOnly("maven.modrinth:raised:${prop("deps.raised")}")
	compileOnly("maven.modrinth:notes:${prop("deps.notes")}")
	// 1.21.1 only mods
	if (stonecutter.eval(stonecutter.current.version, "1.21.1")) {
		compileOnly("maven.modrinth:aileron:${prop("deps.aileron")}")
		compileOnly("maven.modrinth:extrasoundsforge:${prop("deps.extrasounds")}")
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(tasks.named("stonecutterGenerate"))
}
