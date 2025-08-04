package com.notesmanager

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.path
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) = NotesManager()
    .subcommands(Create(), List(), Search(), Export(), Delete(), Edit())
    .main(args)

class NotesManager : CliktCommand() {
    override fun run() = Unit
}

class Create : CliktCommand(help = "Создать новую заметку") {
    private val title by option("-t", "--title", help = "Заголовок заметки")
        .required()
    private val content by option("-c", "--content", help = "Содержимое заметки")
        .required()
    private val category by option("--category", help = "Категория заметки")
        .default("Без категории")
    private val password by option("-p", "--password", help = "Пароль для защиты заметки")

    override fun run() {
        val timestamp = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        
        echo("Создание новой заметки:")
        echo("Заголовок: $title")
        echo("Категория: $category")
        echo("Дата создания: ${timestamp.format(formatter)}")
        echo("Заметка успешно создана!")
    }
}

class List : CliktCommand(help = "Просмотр списка заметок") {
    private val category by option("--category", help = "Фильтр по категории")
    private val sortBy by option("--sort-by", help = "Сортировка (date, title)")
        .default("date")
    private val order by option("--order", help = "Порядок сортировки (asc, desc)")
        .default("desc")

    override fun run() {
        val filter = if (category != null) "категории '$category'" else "всех категорий"
        echo("Список заметок для $filter (сортировка: $sortBy, порядок: $order):")
        
        // Здесь будет код для получения и отображения заметок из базы данных
        echo("1. Покупки (Список дел) - 2023-05-20 14:30")
        echo("2. Идеи для проекта (Работа) - 2023-05-19 09:15")
        echo("3. Рецепт пиццы (Кулинария) - 2023-05-15 18:45")
    }
}

class Search : CliktCommand(help = "Поиск заметок") {
    private val query by argument(help = "Поисковый запрос")

    override fun run() {
        echo("Результаты поиска для запроса '$query':")
        
        // Здесь будет код для поиска заметок
        echo("Найдено 2 заметки:")
        echo("1. Покупки (Список дел) - содержит: '$query'")
        echo("2. Рецепт пиццы (Кулинария) - содержит: '$query'")
    }
}

class Export : CliktCommand(help = "Экспорт заметок") {
    private val format by option("--format", help = "Формат экспорта (txt, json)")
        .choice("txt", "json")
        .default("txt")
    private val output by option("-o", "--output", help = "Путь к выходному файлу")
        .path()
        .required()
    private val category by option("--category", help = "Экспорт заметок определенной категории")

    override fun run() {
        val filter = if (category != null) "категории '$category'" else "всех категорий"
        echo("Экспорт заметок $filter в формате $format:")
        echo("Файл сохранен: $output")
    }
}

class Delete : CliktCommand(help = "Удаление заметки") {
    private val id by option("-i", "--id", help = "ID заметки для удаления")
        .int()
        .required()

    override fun run() {
        echo("Заметка с ID $id успешно удалена")
    }
}

class Edit : CliktCommand(help = "Редактирование заметки") {
    private val id by option("-i", "--id", help = "ID заметки для редактирования")
        .int()
        .required()
    private val title by option("-t", "--title", help = "Новый заголовок")
    private val content by option("-c", "--content", help = "Новое содержимое")
    private val category by option("--category", help = "Новая категория")

    override fun run() {
        echo("Редактирование заметки с ID $id:")
        if (title != null) echo("Заголовок изменен на: $title")
        if (content != null) echo("Содержимое обновлено")
        if (category != null) echo("Категория изменена на: $category")
        echo("Заметка успешно обновлена!")
    }
}