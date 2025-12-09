package com.craftinginterpreters.lox;

abstract class Expr {
  interface Visitor<R> {
    R visitBinaryExpr(Binary expr);
    // ...
  }

  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
      // ...
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }

    final Expr left;
    // ...
  }

  abstract <R> R accept(Visitor<R> visitor);
}