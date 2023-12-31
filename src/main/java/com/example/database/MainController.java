package com.example.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;


import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    UserdetailRepository userdetailRepository;

    @Autowired
    UsertypelinkRepository usertypelinkRepository;

    @GetMapping("/")
    public String getLandingPage() {

        return "landingpage";
    }
    @GetMapping("/usertypelink")
    public String getUserTypelink()
    {
        return "usertype";
    }


    @GetMapping("/interimdashboard")
    public String getInterimDashboard() {

        return "interimdashboard";
    }

    @GetMapping("/userdetails")
    public String userDetails()
    {
        return "userdetails";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username,
                         @RequestParam("password") String password , Model model) {
        Credential existingCredential = credentialRepository.findByUsername(username);
        if (existingCredential != null) {
            model.addAttribute("error", "Username already taken");
            return "landingpage";
        }

        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        credentialRepository.save(credential);

        return "redirect:/interimdashboard";
    }


    @GetMapping("/buyerdashboard")
    public String getBuyersDahsboard() {

        return "buyerdashboard";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpSession session, Model model) {

        Optional<Credential> matchedCredential = credentialRepository.findById(username);
        if (matchedCredential.isPresent()) {
            if (matchedCredential.get().getPassword().equals(password)) {
                session.setAttribute("username", username);

                Optional<Userdetail> userdetail = userdetailRepository.findById(username);
                if (userdetail.isPresent()) {
                    Optional<Usertypelink> usertypelink = usertypelinkRepository.findById(username);
                    if (usertypelink.isPresent()) {
                        if (usertypelink.get().getType().equals("buyer")) {
                            return "buyerdashboard";
                        } else if (usertypelink.get().getType().equals("seller")) {
                            return "sellerdashboard";
                        }
                    }
                    return "userdetails";
                }
                return "sellerdashboard";
            } else {
                model.addAttribute("error", "Invalid password");
                return "landingpage";
            }
        } else {
            model.addAttribute("error", "User not registered");
            return "landingpage";
        }
    }




    @PostMapping("/userdetails")
    public String userDetails(@RequestParam("firstname") String firstname,
                              @RequestParam("lastname") String lastname,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              Model model) {
        Userdetail userdetails = new Userdetail();
        userdetails.setFirstname(firstname);
        userdetails.setLastname(lastname);
        userdetails.setEmail(email);
        userdetails.setPhone(phone);

        userdetailRepository.save(userdetails);

        model.addAttribute("message", "Successfully registered!");
        model.addAttribute("firstname", firstname);
        model.addAttribute("email", email);
        model.addAttribute("phone", phone);
        return "redirect:/usertypelink";

    }


    @PostMapping("/usertypelink")
    public String saveUserTypeLink(@RequestParam("type") String type,
                                   @RequestParam("username") String username, Model model) {

            Usertypelink userTypeLink = new Usertypelink();
            userTypeLink.setType(type);
            userTypeLink.setUsername(username);
            userTypeLink.setId(String.valueOf(Math.floor(Math.random() * 100000)));

            usertypelinkRepository.save(userTypeLink);
            
        return "landingpage";
    }


}


