<template>
  <div id="app">
    <b-navbar toggleable="lg">
      <b-navbar-brand href="/">Plataforma PRC</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav v-if="$auth.loggedIn">
          <b-nav-item-dropdown  right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>Sinais Biomédicos</em>
            </template>
            <b-dropdown-item @click.prevent="goColestrol">Colestrol</b-dropdown-item>
            <b-dropdown-item @click.prevent="goPesagem">Peso/Altura</b-dropdown-item>
            <b-dropdown-item @click.prevent="goBPM">BPM's</b-dropdown-item>
            <b-dropdown-item @click.prevent="goOutro">Outros Sinais</b-dropdown-item>
          </b-nav-item-dropdown>

          <li class="nav-item" v-if="superUser || doctor">
            <nuxt-link class="nav-link" to="/prescricoes/all" >Todas as Prescrições</nuxt-link>
          </li>
          <li class="nav-item" v-else >
            <nuxt-link class="nav-link" to="/prescricoes/my" >Prescrições </nuxt-link>
          </li>
          <li class="nav-item" v-if="superUser || doctor">
            <nuxt-link class="nav-link" to="/listings/utentes" >Utilizadores</nuxt-link>
          </li>
          <li class="nav-item" v-if="superUser">
            <nuxt-link class="nav-link" to="/admin/biosinais/all" >Biosinais</nuxt-link>
          </li>
          <li class="nav-item" v-if="superUser || doctor">
            <nuxt-link class="nav-link" to="/listings/doutores" >Doutores</nuxt-link>
          </li>
          <li class="nav-item" v-if="superUser ">
            <nuxt-link class="nav-link" to="/listings/admins" >Administradores</nuxt-link>
          </li>

        </b-navbar-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown v-if="$auth.loggedIn" right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>{{ $auth.user.sub }}</em>
            </template>
            <b-dropdown-item @click.prevent="perfil">Perfil</b-dropdown-item>
            <b-dropdown-item @click.prevent="signOut">Sair</b-dropdown-item>
          </b-nav-item-dropdown>
          <li class="nav-item" v-else>
            <nuxt-link class="nav-link" to="/auth/login">Sign In</nuxt-link>
          </li>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <main>
      <Nuxt/>
    </main>
  </div>
</template>
<script>
export default {
  methods: {
    signOut() {
      this.$auth.logout()
      this.$router.push('/')
    },
    perfil() {

      if (this.$auth.user.groups =="Doutor"){
        this.$router.push('/profile/'+this.$auth.user.sub+'/doctorProfile')
      }else{
        this.$router.push('/profile/'+this.$auth.user.sub+'/utenteProfile')
      }

    },
    goColestrol() {

      if (this.$auth.user.groups =="UtilizadorNormal"){
        this.$router.push('/biosinais/colestrol/my')
      }
      else {
        this.$router.push('/biosinais/colestrol/all')
      }

    },
    goPesagem() {
      if (this.$auth.user.groups =="UtilizadorNormal"){
        this.$router.push('/biosinais/pesagem/my')
      }
      else {
        this.$router.push('/biosinais/pesagem/all')
      }
    },
    goBPM() {
      if (this.$auth.user.groups =="UtilizadorNormal"){
        this.$router.push('/biosinais/bpms/my')
      }
      else {
        this.$router.push('/biosinais/bpms/all')
      }
    },
    goMyFiles() {
      if (this.$auth.user.groups =="UtilizadorNormal"){
        this.$router.push('/files/my')
      }
      else {
        this.$router.push('/files/all')
      }
    },
    goOutro() {
      if (this.$auth.user.groups =="UtilizadorNormal"){
        this.$router.push('/biosinais/outros/my')
      }
      else {
        this.$router.push('/biosinais/outros/all')
      }
    },

  },
  computed: {
    superUser() {
      if (this.$auth.user.groups =="Administrador"){
        return true;
      }
      else {
        return false;
      }
    },
    doctor() {
      if (this.$auth.user.groups =="Doutor"){
        return true;
      }
      else {
        return false;
      }
    },
    created() {

    },
  }
}
</script>
