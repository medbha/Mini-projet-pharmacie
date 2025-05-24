package com.fst.first.controller;

import com.fst.first.model.Medicament;
import com.fst.first.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medicaments")
public class PharmacieController {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @GetMapping
    public String listMedicaments(@RequestParam(required = false) String keyword, Model model) {
        List<Medicament> medicaments = (keyword != null && !keyword.isEmpty())
                ? medicamentRepository.findByNomContainingIgnoreCase(keyword)
                : medicamentRepository.findAll();
        model.addAttribute("medicaments", medicaments);
        model.addAttribute("keyword", keyword);
        return "medicaments/liste";
    }

    @GetMapping("/ajout")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("medicament", new Medicament());
        return "medicaments/formulaire-ajout";
    }

    @PostMapping("/ajouter")
    public String ajouterMedicament(@ModelAttribute Medicament medicament) {
        medicamentRepository.save(medicament);
        return "redirect:/medicaments";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerMedicament(@PathVariable Long id) {
        medicamentRepository.deleteById(id);
        return "redirect:/medicaments";
    }
}
