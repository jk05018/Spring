package me.develop_han.jpashop.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import me.develop_han.jpashop.domain.Address;
import me.develop_han.jpashop.domain.Member;
import me.develop_han.jpashop.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/members/new")
	public String createForm(Model model){
		model.addAttribute("memberForm", new MemberForm());
		return "members/createMemberFormFile";
	}

	@PostMapping("/members/new")
	public String create(@Valid MemberForm form, BindingResult result){
		if(result.hasErrors()){
			return "members/createMemberFormFile";
		}

		Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
		Member member = new Member();
		member.setName(form.getName());
		member.setAddress(address);
		memberService.join(member);
		return "redirect:/";
	}

	@GetMapping("/members")
	public String list(Model model){
		model.addAttribute("members",memberService.findMembers());
		return "members/memberList";
	}


}
