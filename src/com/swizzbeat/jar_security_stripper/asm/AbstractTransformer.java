package com.swizzbeat.jar_security_stripper.asm;

import org.objectweb.asm.tree.ClassNode;

/**
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/20/14
 */

public abstract class AbstractTransformer {

    public abstract void run(ClassNode node);

}
