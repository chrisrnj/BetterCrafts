package me.chickenstyle.crafts.versions;

import com.epicnicity322.epicpluginlib.bukkit.reflection.ReflectionUtil;
import com.epicnicity322.epicpluginlib.bukkit.reflection.type.DataType;
import com.epicnicity322.epicpluginlib.bukkit.reflection.type.SubPackageType;
import me.chickenstyle.crafts.IDHandler;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public class NonPesistentDataContainerIDHandler implements IDHandler {
    private final @NotNull Class<?> craftItemStack = Objects.requireNonNull(ReflectionUtil.getClass("CraftItemStack", SubPackageType.INVENTORY));
    private final @NotNull Method asNMSCopy = Objects.requireNonNull(ReflectionUtil.getMethod(craftItemStack, "asNMSCopy", ItemStack.class));
    private final @NotNull Method asBukkitCopy = Objects.requireNonNull(ReflectionUtil.getMethod(craftItemStack, "asBukkitCopy", net.minecraft.world.item.ItemStack.class));
    private final @NotNull Method setInt = findMethodByTypeAndParameters(Void.TYPE, String.class, int.class);
    private final @NotNull Method getInt = findMethodByTypeAndParameters(int.class, String.class);
    private final @NotNull Field tagField = Objects.requireNonNull(ReflectionUtil.findFieldByType(net.minecraft.world.item.ItemStack.class, NBTTagCompound.class, false));

    private @NotNull Method findMethodByTypeAndParameters(Class<?> returnType, Class<?>... parameters) {
        Method method = null;

        for (Method m : NBTTagCompound.class.getMethods()) {
            if (m.getReturnType().equals(returnType) && DataType.equalsArray(m.getParameterTypes(), parameters)) {
                method = m;
                break;
            }
        }

        assert method != null;
        return method;
    }

    @Override
    public ItemStack addIDTag(ItemStack item, int data) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = (net.minecraft.world.item.ItemStack) asNMSCopy.invoke(null, item);
            NBTTagCompound itemCompound = (NBTTagCompound) tagField.get(nmsItem);

            if (itemCompound == null) {
                itemCompound = new NBTTagCompound();
                tagField.set(nmsItem, itemCompound);
            }

            setInt.invoke(itemCompound, "IDTag", data);
            return (ItemStack) asBukkitCopy.invoke(null, nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean hasIDTag(ItemStack item) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = (net.minecraft.world.item.ItemStack) asNMSCopy.invoke(null, item);
            NBTTagCompound itemCompound = (NBTTagCompound) tagField.get(nmsItem);

            if (itemCompound == null) return false;

            return getInt.invoke(itemCompound, "IDTag") != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getID(ItemStack item) {
        try {
            net.minecraft.world.item.ItemStack nmsItem = (net.minecraft.world.item.ItemStack) asNMSCopy.invoke(null, item);
            NBTTagCompound itemCompound = (NBTTagCompound) tagField.get(nmsItem);

            if (itemCompound == null) return 0;

            return (int) getInt.invoke(itemCompound, "IDTag");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
