/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.util.List;

/**
 *
 * @author HD
 * @param <T>
 */
public interface Fonctions<T> {
    public List<T> afficher();
    public void supprimer (int id);
    public void modifier (T t, int id);
}
