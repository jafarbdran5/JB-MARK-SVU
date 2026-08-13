package com.example.data.model

enum class ProgramCategory(val labelAr: String, val labelEn: String) {
    UNDERGRADUATE("الإجازات الجامعية (البكالوريوس)", "Undergraduate Programs"),
    POSTGRADUATE("الدبلومات والماجستير", "Postgraduate & Master Programs"),
    INSTITUTE("المعاهد التقانية", "Technical Institutes")
}

enum class PassStatus(
    val titleAr: String,
    val titleEn: String,
    val isSuccess: Boolean
) {
    PASS("ناجح", "Passed", true),
    CONDITIONAL_FAIL("راسب شرطي في الامتحان", "Failed Exam Threshold", false),
    TOTAL_FAIL("راسب بالمحصلة", "Failed Overall Grade", false)
}

data class SvuProgram(
    val id: String,
    val code: String,
    val nameAr: String,
    val nameEn: String,
    val category: ProgramCategory,
    val defaultHwWeight: Double, // e.g., 0.30 or 0.20
    val hasWeightToggle: Boolean, // Can switch between 20% & 30%
    val mcqMaxScore: Double, // e.g. 70.0, 50.0, 100.0, 40.0
    val mcqLabelAr: String,
    val mcqLabelEn: String,
    val essayMaxScore: Double, // e.g. 30.0, 50.0, 0.0, 60.0
    val essayLabelAr: String,
    val essayLabelEn: String,
    val hasEssay: Boolean,
    val minExamPass: Double, // 40.0 for Bachelor, 50.0 for Master
    val minTotalPass: Double, // 50.0 or 60.0
    val descriptionAr: String,
    val descriptionEn: String
)

data class CalculationResult(
    val hwInput: Double,
    val mcqInput: Double,
    val essayInput: Double,
    val examTotal: Double,
    val hwWeighted: Double,
    val examWeighted: Double,
    val finalGrade: Double,
    val displayedGrade: Double, // If conditional fail, equals examTotal per SVU rules
    val status: PassStatus,
    val hwWarningMessageAr: String?,
    val statusMessageAr: String,
    val statusMessageEn: String,
    val hwWeightUsed: Double
)

data class TargetExamScoreResult(
    val hwInput: Double,
    val hwWeightUsed: Double,
    val examWeightUsed: Double,
    val minExamNeeded: Double,
    val isReachable: Boolean,
    val messageAr: String,
    val messageEn: String
)

object SvuPresets {
    val allPrograms = listOf(
        // 1. Law (BL)
        SvuProgram(
            id = "bl",
            code = "BL",
            nameAr = "كلية الحقوق (الإجازة في القانون - BL)",
            nameEn = "Bachelor of Law (BL)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.20,
            hasWeightToggle = true,
            mcqMaxScore = 70.0,
            mcqLabelAr = "قسم الأتمتة (MCQs)",
            mcqLabelEn = "Multiple Choice Section",
            essayMaxScore = 30.0,
            essayLabelAr = "قسم المقالي والمسائل",
            essayLabelEn = "Essay & Cases Section",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | توزيع الوظيفة: 20% أو 30%",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | Homework: 20% or 30%"
        ),
        // 2. Media & Communication (BMC)
        SvuProgram(
            id = "bmc",
            code = "BMC",
            nameAr = "كلية الإعلام والاتصال (BMC)",
            nameEn = "Bachelor of Media & Communication (BMC)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.20,
            hasWeightToggle = true,
            mcqMaxScore = 70.0,
            mcqLabelAr = "قسم الأتمتة (MCQs)",
            mcqLabelEn = "Multiple Choice Section",
            essayMaxScore = 30.0,
            essayLabelAr = "قسم المقالي والمحتوى",
            essayLabelEn = "Essay Section",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | توزيع الوظيفة: 20% أو 30%",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | Homework: 20% or 30%"
        ),
        // 3. Education (EDU)
        SvuProgram(
            id = "edu",
            code = "EDU",
            nameAr = "كلية التربية (التربية وعلم النفس - EDU)",
            nameEn = "Bachelor of Education (EDU)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.20,
            hasWeightToggle = false,
            mcqMaxScore = 70.0,
            mcqLabelAr = "قسم الأتمتة (MCQs)",
            mcqLabelEn = "Multiple Choice Section",
            essayMaxScore = 30.0,
            essayLabelAr = "قسم الأسئلة المقالية",
            essayLabelEn = "Essay Section",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | توزيع: 20% وظيفة + 80% امتحان",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | Homework: 20% + Exam 80%"
        ),
        // 4. IT & Telecom (BAIT / BACT)
        SvuProgram(
            id = "bait",
            code = "BAIT/BACT",
            nameAr = "كلية تقانة المعلومات والاتصالات (BAIT / BACT)",
            nameEn = "Information & Telecom Technology (BAIT / BACT)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.30,
            hasWeightToggle = true,
            mcqMaxScore = 70.0,
            mcqLabelAr = "قسم الأتمتة (MCQs)",
            mcqLabelEn = "MCQ Section",
            essayMaxScore = 30.0,
            essayLabelAr = "قسم البرمجة والمسائل",
            essayLabelEn = "Programming & Problem Solving",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 60.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 60/100 | توزيع الوظيفة: 30% أو 20%",
            descriptionEn = "Min Exam: 40/100 | Min Total: 60/100 | Homework: 30% or 20%"
        ),
        // 5. Civil Engineering (BSCE)
        SvuProgram(
            id = "bsce",
            code = "BSCE",
            nameAr = "كلية الهندسة المدنية (BSCE)",
            nameEn = "Bachelor of Civil Engineering (BSCE)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.30,
            hasWeightToggle = false,
            mcqMaxScore = 50.0,
            mcqLabelAr = "قسم الأتمتة (من 50)",
            mcqLabelEn = "MCQ Section (out of 50)",
            essayMaxScore = 50.0,
            essayLabelAr = "المسائل الكتابية والمخططات (من 50)",
            essayLabelEn = "Written Problems & Diagrams (out of 50)",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 60.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 60/100 | 30% مشروع/وظيفة + 70% امتحان",
            descriptionEn = "Min Exam: 40/100 | Min Total: 60/100 | 30% Project + 70% Exam"
        ),
        // 6. Business Administration (BBA)
        SvuProgram(
            id = "bba",
            code = "BBA",
            nameAr = "كلية إدارة الأعمال (BBA)",
            nameEn = "Bachelor of Business Administration (BBA)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.30,
            hasWeightToggle = false,
            mcqMaxScore = 100.0,
            mcqLabelAr = "علامة الأتمتة الكلية (من 100)",
            mcqLabelEn = "Full MCQ Exam (out of 100)",
            essayMaxScore = 0.0,
            essayLabelAr = "لا يوجد مقالي",
            essayLabelEn = "No Essay",
            hasEssay = false,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | 30% وظيفة + 70% أتمتة شاملة",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | 30% Homework + 70% MCQ"
        ),
        // 7. Languages & Translation (BTL)
        SvuProgram(
            id = "btl",
            code = "BTL",
            nameAr = "كلية اللغات والترجمة (BTL)",
            nameEn = "Bachelor of Languages & Translation (BTL)",
            category = ProgramCategory.UNDERGRADUATE,
            defaultHwWeight = 0.30,
            hasWeightToggle = false,
            mcqMaxScore = 50.0,
            mcqLabelAr = "قسم الأتمتة والقواعد (من 50)",
            mcqLabelEn = "MCQ & Grammar (out of 50)",
            essayMaxScore = 50.0,
            essayLabelAr = "قسم الترجمة والمقال (من 50)",
            essayLabelEn = "Translation & Essay (out of 50)",
            hasEssay = true,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | 30% وظيفة + 70% امتحان",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | 30% Homework + 70% Exam"
        ),
        // 8. Technical Institutes (TIC / TBM / THM)
        SvuProgram(
            id = "institutes",
            code = "TIC/TBM/THM",
            nameAr = "المعاهد التقانية (الحاسوبي TIC - إدارة الأعمال TBM - السياحي THM)",
            nameEn = "Higher Technical Institutes (TIC / TBM / THM)",
            category = ProgramCategory.INSTITUTE,
            defaultHwWeight = 0.30,
            hasWeightToggle = true,
            mcqMaxScore = 100.0,
            mcqLabelAr = "علامة الامتحان النهائي (أتمتة/كتابي من 100)",
            mcqLabelEn = "Final Written/MCQ Exam (out of 100)",
            essayMaxScore = 0.0,
            essayLabelAr = "لا يوجد مقالي منفصل",
            essayLabelEn = "No Separate Essay",
            hasEssay = false,
            minExamPass = 40.0,
            minTotalPass = 50.0,
            descriptionAr = "أدنى علامة امتحان: 40/100 | نجاح المحصلة: 50/100 | 30% وظيفة (أو 20%) + 70% امتحان",
            descriptionEn = "Min Exam: 40/100 | Min Total: 50/100 | 30% HW + 70% Exam"
        ),
        // 9. Postgraduate & Master Programs (MBA - MWS - MISE - MQM - ITE - Masters)
        SvuProgram(
            id = "masters",
            code = "PG / Master",
            nameAr = "برامج الماجستير والدبلومات العالية (MBA / MWS / MISE / MQM / ITE)",
            nameEn = "Postgraduate & Master Programs",
            category = ProgramCategory.POSTGRADUATE,
            defaultHwWeight = 0.30,
            hasWeightToggle = false,
            mcqMaxScore = 40.0,
            mcqLabelAr = "الأتمتة المفاهيمية (من 40)",
            mcqLabelEn = "Conceptual MCQ (out of 40)",
            essayMaxScore = 60.0,
            essayLabelAr = "المقالي والحالات الدراسية (من 60)",
            essayLabelEn = "Essay & Case Studies (out of 60)",
            hasEssay = true,
            minExamPass = 50.0,
            minTotalPass = 60.0,
            descriptionAr = "أدنى علامة امتحان: 50/100 | نجاح المحصلة: 60/100 | 30% وظائف وأعمال + 70% كتابي",
            descriptionEn = "Min Exam: 50/100 | Min Total: 60/100 | 30% Homework + 70% Written Exam"
        )
    )

    fun calculateGrade(
        program: SvuProgram,
        hwWeight: Double,
        hwInput: Double,
        mcqInput: Double,
        essayInput: Double
    ): CalculationResult {
        val examTotal = mcqInput + essayInput
        val hwWeighted = hwInput * hwWeight
        val examWeight = 1.0 - hwWeight
        val examWeighted = examTotal * examWeight
        val finalGrade = hwWeighted + examWeighted

        val hwWarning = if (hwInput < 40.0) {
            "تنبيه: علامة الوظيفة أقل من 40/100."
        } else null

        val status: PassStatus
        val statusMessageAr: String
        val statusMessageEn: String
        val displayedGrade: Double

        if (examTotal < program.minExamPass) {
            status = PassStatus.CONDITIONAL_FAIL
            displayedGrade = examTotal
            statusMessageAr = "راسب شرطي في الامتحان: لم تحقق شرط الامتحان الأدنى (${program.minExamPass.toInt()}/100). تم تسجيل المحصلة مساوية لعلامة الامتحان فقط دون الوظيفة."
            statusMessageEn = "Conditional Fail: Exam score below minimum threshold (${program.minExamPass.toInt()}/100). Grade equals exam score only."
        } else if (finalGrade < program.minTotalPass) {
            status = PassStatus.TOTAL_FAIL
            displayedGrade = finalGrade
            statusMessageAr = "راسب بالمحصلة: المحصلة الكلية (${String.format("%.2f", finalGrade)}) أقل من شرط النجاح النهائي (${program.minTotalPass.toInt()}/100)."
            statusMessageEn = "Failed Overall: Total score (${String.format("%.2f", finalGrade)}) is below pass threshold (${program.minTotalPass.toInt()}/100)."
        } else {
            status = PassStatus.PASS
            displayedGrade = finalGrade
            statusMessageAr = "ناجح: مبارك! تم استيفاء شرط الامتحان وشرط المحصلة الكلية بنجاح."
            statusMessageEn = "Passed: Congratulations! All pass requirements have been satisfied."
        }

        return CalculationResult(
            hwInput = hwInput,
            mcqInput = mcqInput,
            essayInput = essayInput,
            examTotal = examTotal,
            hwWeighted = hwWeighted,
            examWeighted = examWeighted,
            finalGrade = finalGrade,
            displayedGrade = displayedGrade,
            status = status,
            hwWarningMessageAr = hwWarning,
            statusMessageAr = statusMessageAr,
            statusMessageEn = statusMessageEn,
            hwWeightUsed = hwWeight
        )
    }

    fun calculateTargetExamScore(
        program: SvuProgram,
        hwWeight: Double,
        hwInput: Double
    ): TargetExamScoreResult {
        val examWeight = 1.0 - hwWeight
        val neededForTotalPass = Math.ceil((program.minTotalPass - (hwInput * hwWeight)) / examWeight)
        val minRequiredExamScore = Math.max(program.minExamPass, neededForTotalPass)
        val isReachable = minRequiredExamScore <= 100.0

        val msgAr = if (!isReachable) {
            "غير ممكن للأسف: علامة الوظيفة لمتعة الوصول لعلامة المحصلة حتى لو حصلت على 100/100 في الامتحان."
        } else {
            "أقل مجموع درجات تحتاج للحصول عليه في الامتحان النهائي (أتمتة + مقالي) لتضمن النجاح هو: ${minRequiredExamScore.toInt()} / 100"
        }

        val msgEn = if (!isReachable) {
            "Unreachable: Homework mark is too low to pass even with 100/100 in final exam."
        } else {
            "Minimum final exam total (MCQ + Essay) required to pass: ${minRequiredExamScore.toInt()} / 100"
        }

        return TargetExamScoreResult(
            hwInput = hwInput,
            hwWeightUsed = hwWeight,
            examWeightUsed = examWeight,
            minExamNeeded = if (isReachable) minRequiredExamScore else 100.0,
            isReachable = isReachable,
            messageAr = msgAr,
            messageEn = msgEn
        )
    }
}
