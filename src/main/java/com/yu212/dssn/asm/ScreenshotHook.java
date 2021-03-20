package com.yu212.dssn.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ScreenshotHook implements IClassTransformer {
    private static final String TARGET_CLASS_NAME = "net.minecraft.client.Minecraft";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!transformedName.equals(TARGET_CLASS_NAME)) {
            return basicClass;
        }
        ClassReader reader = new ClassReader(basicClass);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM4, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
                if (ClassTransformerUtils.mapMethodName(TARGET_CLASS_NAME, name, desc).equals("func_152348_aa")) {
                    return new MethodVisitor(Opcodes.ASM4, methodVisitor) {
                        @Override
                        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                            String className = ClassTransformerUtils.mapClassName(owner);
                            if (opcode != Opcodes.INVOKEVIRTUAL || !className.equals("net.minecraft.client.gui.GuiNewChat")) {
                                super.visitMethodInsn(opcode, owner, name, desc, itf);
                            } else {
                                visitInsn(Opcodes.POP);
                                visitInsn(Opcodes.POP);
                            }
                        }
                    };
                }
                return methodVisitor;
            }
        };
        reader.accept(classVisitor, 0);
        return writer.toByteArray();
    }
}
