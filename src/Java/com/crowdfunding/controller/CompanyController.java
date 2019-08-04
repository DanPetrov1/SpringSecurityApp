package com.crowdfunding.controller;

import com.crowdfunding.model.Company;
import com.crowdfunding.repository.CompanyRepository;
import com.crowdfunding.service.CompanyServiceImplementation;
import com.crowdfunding.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    CompanyServiceImplementation companyServiceImplementation;

    @RequestMapping(name = "/companies", method = RequestMethod.GET)
    public String companies(Model model, String error, String message) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        if(message != null) {
            model.addAttribute("message", message);
        }

        if(error != null) {
            model.addAttribute("error", error);
        }

        int cash = 0;
        model.addAttribute("cash", cash);
        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("newCompany", new Company());

        return "companies";
    }

    @RequestMapping(name = "/company={id}/share={cash}", method = RequestMethod.GET)
    public String share(@PathVariable("id") int id, @PathVariable("cash") int cash, Model model) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }
        if(userServiceImplementation.getCurrentUserRole().getId() == 1L ||
                userServiceImplementation.getCurrentUserRole().getId() == 4L) {
            model.addAttribute("error", "Access is denied.");
            return "redirect:http://localhost:8087/companies";
        }

        if (cash < 1 || userServiceImplementation.getCurrentUser().getCash() < cash) {
            model.addAttribute("error", "Write the correct sum to share");
            return "redirect:http://localhost:8087/companies";
        }

        companyServiceImplementation.share(companyRepository.findById(id), cash);
        model.addAttribute("message", "Sharing completed successfully");

        return "redirect:http://localhost:8087/companies";
    }
}
