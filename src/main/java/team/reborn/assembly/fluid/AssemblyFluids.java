package team.reborn.assembly.fluid;

import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey.FluidKeyBuilder;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.SimpleFluidKey;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.block.Block;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.assembly.Assembly;

import java.util.LinkedHashMap;
import java.util.Map;

public class AssemblyFluids {

	private static final Map<Identifier, AssemblyFluid> FLUIDS = new LinkedHashMap<>();
	private static final BiMap<AssemblyFluid, AssemblyFluid> STILL_FLOWING_MAP = HashBiMap.create();

	public static AssemblyFluid LATEX = add(new AssemblyFluid.Settings("latex").tickRate(15));
	public static AssemblyFluid FLOWING_LATEX = getFlowing(LATEX);
//	public static AssemblyFluid BIOMASS = add(new AssemblyFluid.Settings("biomass").tickRate(8));
//	public static AssemblyFluid FLOWING_BIOMASS = getFlowing(BIOMASS);
//	public static AssemblyFluid OIL = add(new AssemblyFluid.Settings("oil").tickRate(20));
//	public static AssemblyFluid FLOWING_OIL = getFlowing(OIL);
	public static AssemblyFluid STEAM = add(new AssemblyFluid.Settings("steam").tickRate(1).fluidKey(FluidKey.FluidKeyBuilder::setGas));
	public static AssemblyFluid FLOWING_STEAM = getFlowing(STEAM);

	public static AssemblyFluid add(AssemblyFluid.Settings settings) {
		AssemblyFluid still = new AssemblyFluid.Still(settings);
		AssemblyFluid flowing = new AssemblyFluid.Flowing(settings);
		putFluid(settings.getId(), still);
		putFluid(new Identifier(Assembly.MOD_ID, "flowing_" + settings.getName()), flowing);
		STILL_FLOWING_MAP.put(still, flowing);
		if (settings.fluidKeyBuilder != null) {
		    FluidKeyBuilder builder = new FluidKeyBuilder(still);
	        builder.setName(new TranslatableText("block.assembly." + settings.getName()));
		    settings.fluidKeyBuilder.accept(builder);
		    FluidKeys.put(still, new SimpleFluidKey(builder));
		}
		return still;
	}

	private static void putFluid(Identifier id, AssemblyFluid fluid) {
	    FLUIDS.put(id, fluid);
	    Registry.register(Registry.FLUID, id, fluid);
	}

    public static AssemblyFluid getFlowing(AssemblyFluid still) {
		return STILL_FLOWING_MAP.get(still);
	}

	public static AssemblyFluid getStill(AssemblyFluid flowing) {
		return STILL_FLOWING_MAP.inverse().get(flowing);
	}

	public static AssemblyFluid getInverse(AssemblyFluid fluid) {
		return STILL_FLOWING_MAP.containsKey(fluid) ? STILL_FLOWING_MAP.get(fluid) : STILL_FLOWING_MAP.inverse().get(fluid);
	}

	public static void register() {
	    // Just to call clinit
	}

	public static Map<Identifier, AssemblyFluid> getFluids() {
		return FLUIDS;
	}
}
