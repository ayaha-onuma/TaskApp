package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@PostMapping
	public ResponseEntity<Task> createTask(@RequestBody Task task) {
		Task savedTask = taskRepository.save(task);
		return ResponseEntity.ok(savedTask);
	}

	@PostMapping("/add")
	public String addTask(Task task) {
		taskRepository.save(task);
		return "redirect:/tasks"; // タスク一覧にリダイレクト
	}

	@PostMapping("/delete/{id}")
	public String deleteTask(@PathVariable("id") Long id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks"; // タスク一覧にリダイレクト
	}

	@Autowired
	private TaskRepository taskRepository;

	@GetMapping
	public String getAllTasks(Model model) {
		List<Task> tasks = taskRepository.findAll();
		model.addAttribute("tasks", tasks);
		model.addAttribute("task", new Task());
		return "task-list"; // ← "task-list.html" を表示！
	}
	
	@GetMapping("/edit/{id}")
	public String editTask(@PathVariable("id") Long id, Model model) {
	    Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
	    model.addAttribute("task", task);
	    return "edit-task"; // 編集ページへ！
	}
	
	@PostMapping("/update")
	public String updateTask(Task task) {
	    taskRepository.save(task);
	    return "redirect:/tasks"; // 更新後、一覧ページに戻る！
	}


}
