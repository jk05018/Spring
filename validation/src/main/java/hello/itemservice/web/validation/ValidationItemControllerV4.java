package hello.itemservice.web.validation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

	private final ItemRepository itemRepository;

	@GetMapping
	public String items(Model model) {
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "validation/v4/items";
	}

	@GetMapping("/{itemId}")
	public String item(@PathVariable long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v4/item";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("item", new Item());
		return "validation/v4/addForm";
	}

	// @PostMapping("/add")
	public String addItem1(@Validated @ModelAttribute Item item, BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		Model model) {

		// 특정 필드가 아닌 복합 룰 검증
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice <= 10000) {
				bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		// 검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "validation/v4/addForm";
		}

		// 상품 저장 로직
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v4/items/{itemId}";
	}

	// @PostMapping("/add")
	public String addItem2(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		Model model) {

		// 특정 필드가 아닌 복합 룰 검증
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice <= 10000) {
				bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		// 검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "validation/v4/addForm";
		}

		// 상품 저장 로직
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v4/items/{itemId}";
	}

	@PostMapping("/add")
	public String addItem3(@Validated @ModelAttribute("item") ItemSaveForm saveForm, BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		Model model) {

		// 특정 필드가 아닌 복합 룰 검증
		if (saveForm.getPrice() != null && saveForm.getQuantity() != null) {
			int resultPrice = saveForm.getPrice() * saveForm.getQuantity();
			if (resultPrice <= 10000) {
				bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		// 검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "validation/v4/addForm";
		}

		// 상품 저장 로직
		Item item = new Item(saveForm.getItemName(), saveForm.getPrice(), saveForm.getQuantity());
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/validation/v4/items/{itemId}";
	}

	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable Long itemId, Model model) {
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		return "validation/v4/editForm";
	}
	// @PostMapping("/{itemId}/edit")
	public String edit(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item,
		BindingResult bindingResult) {
		// 특정 필드가 아닌 복합 룰 검증
		if (item.getPrice() != null && item.getQuantity() != null) {
			int resultPrice = item.getPrice() * item.getQuantity();
			if (resultPrice <= 10000) {
				bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		// 검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "validation/v4/editForm";
		}

		itemRepository.update(itemId, item);
		return "redirect:/validation/v4/items/{itemId}";
	}

	@PostMapping("/{itemId}/edit")
	public String edit2(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm updateForm,
		BindingResult bindingResult) {
		// 특정 필드가 아닌 복합 룰 검증
		if (updateForm.getPrice() != null && updateForm.getQuantity() != null) {
			int resultPrice = updateForm.getPrice() * updateForm.getQuantity();
			if (resultPrice <= 10000) {
				bindingResult.reject("totalPriceMin", new Object[] {10000, resultPrice}, null);
			}
		}

		// 검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors = {}", bindingResult);
			return "validation/v4/editForm";
		}

		final Item item = itemRepository.findById(itemId);
		item.setItemName(updateForm.getItemName());
		item.setPrice(updateForm.getPrice());
		item.setQuantity(updateForm.getQuantity());

		itemRepository.update(itemId, item);
		return "redirect:/validation/v4/items/{itemId}";
	}

}

