package com.crowdfunding.controller;

import com.crowdfunding.model.Company;
import com.crowdfunding.model.Role;
import com.crowdfunding.repository.CompanyRepository;
import com.crowdfunding.service.CompanyServiceImplementation;
import com.crowdfunding.service.UserServiceImplementation;
import com.crowdfunding.validator.CompanyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CompanyController {

    @Autowired
    UserServiceImplementation userServiceImplementation;

    @Autowired
    CompanyServiceImplementation companyServiceImplementation;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyValidator companyValidator;

    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public String companies(Model model, String message, String error) {
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

        model.addAttribute("companies", companyRepository.findAll());
        model.addAttribute("newCompany", new Company());

        return "companies";
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public String addCompany(@ModelAttribute("newCompany") Company company, BindingResult bindingResult, Model model) {
        if (company != null) {
            companyValidator.validate(company, bindingResult);
            if(bindingResult.hasErrors()) {
                return "companies";
            }

            companyServiceImplementation.addCompany(company);
            model.addAttribute("message", "Company has been founded successfully.");
        }

        return "redirect:http://localhost:8087/companies";
    }

    @RequestMapping(value = "/company={id}/share", method = RequestMethod.GET)
    public String shareCash(@PathVariable("id") int id, @ModelAttribute("cash") int cash, Model model) {
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

    @RequestMapping(value = "/company={id}", method = RequestMethod.GET)
    public String editCompany(@PathVariable int id, String message, String error, Model model) {
        if(userServiceImplementation.getCurrentUser() == null) {
            model.addAttribute("message", "You need to log in.");
            return "redirect:http://localhost:8087/login";
        }

        Company company = companyRepository.findById(id);
        model.addAttribute("company", company);
        model.addAttribute("user", userServiceImplementation.getCurrentUser());

        if(message != null) {
            model.addAttribute("message", message);
        }

        if(error != null) {
            model.addAttribute("error", error);
        }

        Role role = userServiceImplementation.getCurrentUserRole();
        if (userServiceImplementation.isCurrentUser(id) || role.getId() == 3L) {
            model.addAttribute("editCompany", new Company());
        }
        if (role.getId() == 1L || role.getId() == 4L) {
            model.addAttribute("noRole", role);
        }

        return "company";
    }

    @RequestMapping(value = "/company={id}", method = RequestMethod.POST)
    public String editCompany(@PathVariable int id, @ModelAttribute("editCompany") Company editCompany,
                              BindingResult bindingResult, Model model) {
        if (editCompany != null) {
            companyValidator.validate(editCompany, bindingResult);
            if (bindingResult.hasErrors()) {
                return "/company";
            }

            companyServiceImplementation.update(companyRepository.findById(id), editCompany);
            model.addAttribute("message", "Company info was updated successfully.");
        }

        return "redirect:http://localhost:8087/company=" + id;
    }
}
