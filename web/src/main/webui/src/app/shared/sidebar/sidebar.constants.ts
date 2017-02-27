export class SidebarConstants {
  public static get admin() { return [
      {route: 'osbb' , name: 'osbb' , class: 'fa fa-home fa-fw fa-4x'},
      {route: 'houses' , name: 'houses' , class: 'fa fa-user'},
      {route: 'apartments' , name: 'rents' , class: 'fa fa-building-o'},
      {route: 'ticket' , name: 'tickets' , class: 'fa fa-ticket'},
      {route: 'calendar' , name: 'calendar' , class: 'fa fa-calendar'},
      {route: 'events' , name: 'events' , class: 'fa fa-comments'},
      {route: 'attachments' , name: 'attachments' , class: 'fa fa-cloud-download'},
      {route: 'users' , name: 'users' , class: 'fa fa-users'},
      {route: 'role' , name: 'roles' , class: 'fa fa-building-o'}
    ]; }
  public static get manager() { return [
      {route: 'houses' , name: 'houses' , class: 'fa fa-user'},
      {route: 'apartments' , name: 'rents' , class: 'fa fa-building-o'},
      {route: 'ticket' , name: 'tickets' , class: 'fa fa-ticket'},
      {route: 'calendar' , name: 'calendar' , class: 'fa fa-calendar'},
      {route: 'events' , name: 'events' , class: 'fa fa-comments'},
      {route: 'osbb' , name: 'bills_osbb' , class: 'fa fa-credit-card-alt'},
      {route: 'provider' , name: 'providers' , class: 'fa fa-building'},
      {route: 'contract' , name: 'contracts' , class: 'fa fa-bookmark'}
    ]; }
  public static get user() { return [
      {route: 'ticket' , name: 'tickets' , class: 'fa fa-ticket'},
      {route: 'calendar' , name: 'calendar' , class: 'fa fa-calendar'},
      {route: 'events' , name: 'events' , class: 'fa fa-comments'},
      {route: 'provider/info' , name: 'providers' , class: 'fa fa-building-o'},
      {route: 'report' , name: 'reports' , class: 'fa fa-list-alt'}
    ]; }
  public static get providers() { return [
      {route: 'rents/electricity' , name: 'electricity'},
      {route: 'rents/gas' , name: 'gas'},
      {route: 'rents/water' , name: 'water'},
      {route: 'rents/service' , name: 'debt'},
      {route: 'bill/parentbillid' , name: 'rate'},
  ]; }
}
