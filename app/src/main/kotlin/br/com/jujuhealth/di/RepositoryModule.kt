package br.com.jujuhealth.di

import br.com.jujuhealth.data.request.calendar.ServiceCalendar
import br.com.jujuhealth.data.request.calendar.ServiceCalendarContract
import br.com.jujuhealth.data.request.main.ServiceMain
import br.com.jujuhealth.data.request.main.ServiceMainContract
import br.com.jujuhealth.data.request.sign.ServiceAuth
import br.com.jujuhealth.data.request.sign.ServiceAuthContract
import org.koin.dsl.module

val repositoryModule = module {
    single { ServiceAuth(get(), get()) as ServiceAuthContract }
    single { ServiceMain(get(), get()) as ServiceMainContract }
    single { ServiceCalendar(get(), get()) as ServiceCalendarContract }
}