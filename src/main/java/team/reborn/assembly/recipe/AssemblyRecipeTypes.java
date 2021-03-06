package team.reborn.assembly.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import team.reborn.assembly.Assembly;

import java.util.LinkedHashMap;
import java.util.Map;

public class AssemblyRecipeTypes {

	private static final Map<Identifier, RecipeType<?>> RECIPE_TYPES = new LinkedHashMap<>();

	public static final RecipeType<BoilingRecipe> BOILING = add("boiling");
	public static final RecipeType<SteamPressingRecipe> STEAM_PRESSING = add("steam_pressing");

	private static <R extends Recipe<? extends Inventory>> RecipeType<R> add(String name) {
		Identifier id = new Identifier(Assembly.MOD_ID, name);
		RecipeType<R> recipeType = new RecipeType<R>() {
			public String toString() {
				return id.toString();
			}
		};
		RECIPE_TYPES.put(id, recipeType);
		return recipeType;
	}

	public static void register() {
		for (Identifier id : RECIPE_TYPES.keySet()) {
			Registry.register(Registry.RECIPE_TYPE, id, RECIPE_TYPES.get(id));
		}
	}


}
