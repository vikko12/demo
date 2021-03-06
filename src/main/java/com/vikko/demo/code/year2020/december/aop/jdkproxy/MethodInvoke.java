package com.vikko.demo.code.year2020.december.aop.jdkproxy;

import java.lang.reflect.Method;

/**
 * @author: vikko
 * @Date: 2020/12/1 14:06
 * @Description:
 */
public class MethodInvoke {

	public static void main(String[] args) throws Exception {
		Method animalMethod = Animal.class.getDeclaredMethod("print");
		Method catMethod = Cat.class.getDeclaredMethod("print");

		Animal animal = new Animal();
		Cat cat = new Cat();
		animalMethod.invoke(cat);
		animalMethod.invoke(animal);

		catMethod.invoke(cat);
		catMethod.invoke(animal);
	}

}

class Animal {

	public void print() {
		System.out.println("Animal.print()");
	}

}

class Cat extends Animal {

	@Override
	public void print() {
		System.out.println("Cat.print()");
	}

}
