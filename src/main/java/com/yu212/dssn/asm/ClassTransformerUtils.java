package com.yu212.dssn.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class ClassTransformerUtils {
    public static String mapClassName(String name) {
        return FMLDeobfuscatingRemapper.INSTANCE.map(name.replace('.', '/')).replace('/', '.');
    }

    public static String unmapClassName(String name) {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(name.replace('.', '/')).replace('/', '.');
    }

    public static String mapMethodName(String owner, String name, String desc) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(unmapClassName(owner), name, desc);
    }

    public static String mapFieldName(String owner, String name, String desc) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(unmapClassName(owner), name, desc);
    }
}
