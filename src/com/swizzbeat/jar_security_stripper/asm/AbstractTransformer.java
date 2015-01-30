package com.swizzbeat.jar_security_stripper.asm;

import org.objectweb.asm.tree.ClassNode;

/**
 * Credits to Cov for the framework idea
 * 
 * Created with IntelliJ IDEA
 * User: Anthony
 * Date: 12/20/14
 */

public abstract class AbstractTransformer {

    public abstract void run(ClassNode node);

}
