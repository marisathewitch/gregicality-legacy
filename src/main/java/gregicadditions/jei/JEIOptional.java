package gregicadditions.jei;

import gregicadditions.GAValues;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.render.scene.WorldSceneRenderer;
import gregtech.integration.jei.multiblock.MultiblockInfoRecipeWrapper;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

import java.util.List;

public class JEIOptional {

    @Optional.Method(modid = GAValues.MODID_JEI)
    @SuppressWarnings("unchecked")
    public static WorldSceneRenderer getWorldSceneRenderer(MultiblockControllerBase controllerBase){
        IRecipeRegistry rr = JEIGAPlugin.jeiRuntime.getRecipeRegistry();
        IFocus<ItemStack> focus = rr.createFocus(IFocus.Mode.INPUT, controllerBase.getStackForm());
        return rr.getRecipeCategories(focus)
                .stream()
                .map(c -> (IRecipeCategory<IRecipeWrapper>) c)
                .map(c -> rr.getRecipeWrappers(c, focus))
                .flatMap(List::stream)
                .filter(MultiblockInfoRecipeWrapper.class::isInstance)
                .findFirst()
                .map(MultiblockInfoRecipeWrapper.class::cast)
                .map(MultiblockInfoRecipeWrapper::getCurrentRenderer)
                .orElse(null);
    }
}
