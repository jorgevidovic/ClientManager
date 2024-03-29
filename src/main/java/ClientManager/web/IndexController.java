package ClientManager.web;

import ClientManager.domain.Person;
import ClientManager.service.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private PersonService personServ;

    @GetMapping("/")
    public String start(Model model) {
        var persons = personServ.listPersons();
        model.addAttribute("persons", persons);
        var totalBalance = 0D;
        for (var p : persons) {
            totalBalance += p.getSaldo();
        }
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("totalClients", persons.size());
        return "index";
    }

    @GetMapping("/addPerson")
    public String addPerson(Person person) {
        return "modifyPerson";
    }

    @PostMapping("/savePerson")
    public String savePerson(@Valid Person person, Errors er) {
        if (er.hasErrors()) {
            return "modifyPerson";
        }
        personServ.savePerson(person);
        return "redirect:/";
    }

    @GetMapping("/editPerson/{id_persona}")
    public String editPerson(Person person, Model model) {
        person = personServ.findPerson(person);
        model.addAttribute("person", person);
        return "modifyPerson";
    }

    @GetMapping("/deletePerson/{id_persona}")
    public String deletePerson(Person person) {
        personServ.deletePerson(person);
        return "redirect:/";
    }
}